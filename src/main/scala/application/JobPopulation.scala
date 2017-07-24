package application

import factories.{DatabaseFactory, Resources, StreamingFactory}
import services.{Keeper, Miner}
import utils.{Converter, SparkUtils}


object JobPopulation {
  def main(args: Array[String]) {
    val ssc = StreamingFactory.getStreamingContext
    ssc.checkpoint(Resources.getStreamCheckpointPath)

    val lines = ssc.socketTextStream(Resources.getHost, Resources.getStreamPort)
    val ds = Converter.linesToCities(lines)
    val cities = ds.map(city => (city.name, city))

    val selected_rows = cities.updateStateByKey(SparkUtils.recentlyCities)
    val countries = selected_rows
      .map(pair => (pair._2.country, pair._2.population))

    val finishedInfo = countries.updateStateByKey(Miner.getPopulation)

    finishedInfo.foreachRDD(rdd =>
      Keeper.savePopulation(rdd, DatabaseFactory.getPopulationCollection))

    ssc.start()
    ssc.awaitTerminationOrTimeout(5*1000) // 5 sec

  }
}
