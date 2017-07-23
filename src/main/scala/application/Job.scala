package application

import factories.{Resources, StreamingFactory}
import utils.Converter


object Job {
  def main(args: Array[String]) {
    val ssc = StreamingFactory.getStreamingContext

    val lines = ssc.socketTextStream("localhost", Resources.getStreamPort)
    val ds = Converter.linesToCities(lines)

    ds.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
