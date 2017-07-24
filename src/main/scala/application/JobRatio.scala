package application

import factories.{DatabaseFactory, Resources, StreamingFactory}
import services.{Keeper, Miner}
import utils.{Converter, SparkUtils}

object JobRatio {
  def main(args: Array[String]): Unit = {
    val ssc = StreamingFactory.getStreamingContext
    ssc.checkpoint(Resources.getStreamCheckpointPath)

    val lines = ssc.socketTextStream(Resources.getHost, Resources.getStreamPort)
    val ds = Converter.linesToCities(lines)

    val m_part = ds.filter(city => city.sex == 'm')
    val f_part = ds.filter(city => city.sex == 'f')

    val m_cities = m_part.map(city => (city.name, city))
    val f_cities = f_part.map(city => (city.name, city))

    val m_selected_rows = m_cities.updateStateByKey(SparkUtils.recentlyCities)
    val f_selected_rows = f_cities.updateStateByKey(SparkUtils.recentlyCities)

    val m_countries = m_selected_rows.map(pair => (pair._2.country, pair._2.population))
    val f_countries = f_selected_rows.map(pair => (pair._2.country, pair._2.population))

    val m_finishedInfo = m_countries.updateStateByKey(Miner.getPopulation)
    val f_finishedInfo = f_countries.updateStateByKey(Miner.getPopulation)

    val res = m_finishedInfo.join(f_finishedInfo).mapValues(pair => pair._1 / pair._2.toDouble)

    res.foreachRDD(rdd =>
      Keeper.saveRatio(rdd, DatabaseFactory.getRatioCollection))

    ssc.start()
    ssc.awaitTerminationOrTimeout(5*1000) // 5 sec
  }
}
