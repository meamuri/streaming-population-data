package application

import factories.{Resources, StreamingFactory}
import services.Miner
import utils.Converter


object Job {
  def main(args: Array[String]) {
    val ssc = StreamingFactory.getStreamingContext
    ssc.checkpoint("tmp")

    val lines = ssc.socketTextStream("localhost", Resources.getStreamPort)
    val ds = Converter.linesToCities(lines)
    val cities = ds.map(city => (city.name, city))

    val selected_rows = cities.updateStateByKey(Miner.update)

    selected_rows.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
