package application

import factories.{DatabaseFactory, Resources, StreamingFactory}
import services.{Keeper, Miner}
import utils.{Converter, SparkUtils}

object JobMillionaires {
  def main(args: Array[String]): Unit = {
    val ssc = StreamingFactory.getStreamingContext
    ssc.checkpoint(Resources.getStreamCheckpointPath)

    val lines = ssc.socketTextStream(Resources.getHost, Resources.getStreamPort)
    val ds = Converter.linesToCities(lines)
    val cities = ds
      .filter(city => city.population > Resources.getLevel)
      .map(city => (city.name, city))

    val selected_rows = cities.updateStateByKey(SparkUtils.recentlyCities)

    val countries = selected_rows
      .map(pair => (pair._2.country, pair._2))

    val finishedInfo = countries.updateStateByKey(Miner.getCount)

    finishedInfo.foreachRDD(rdd =>
      Keeper.saveMillionaires(rdd, DatabaseFactory.getMillionairesCollection))

    ssc.start()
    ssc.awaitTerminationOrTimeout(5*1000) // 5 sec

  }
}
