package application

import factories.{Resources, StreamingFactory}
import services.Miner
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

    finishedInfo.print()

    ssc.start()
    ssc.awaitTerminationOrTimeout(5*1000) // 5 sec

  }
}
