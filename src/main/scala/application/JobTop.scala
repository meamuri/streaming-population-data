package application

import factories.{DatabaseFactory, Resources, StreamingFactory}
import services.{Keeper, Miner}
import utils.{Converter, SparkUtils}

object JobTop {
  def main(args: Array[String]): Unit = {
    val ssc = StreamingFactory.getStreamingContext
    ssc.checkpoint(Resources.getStreamCheckpointPath)

    val lines = ssc.socketTextStream(Resources.getHost, Resources.getStreamPort)
    val ds = Converter.linesToCities(lines)
    val cities = ds
      .map(city => (city.name, city))

    val selected_rows = cities.updateStateByKey(SparkUtils.recentlyCities)

    val countries = selected_rows
      .map(pair => (pair._2.country, pair._2))

    val finishedInfo = countries.updateStateByKey(Miner.getTop)

    finishedInfo.foreachRDD(rdd => Keeper.saveTop(rdd, DatabaseFactory.getTopCollection))

    ssc.start()
    ssc.awaitTerminationOrTimeout(5*1000) // 5 sec

  }
}
