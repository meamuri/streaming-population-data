package application

import factories.{Resources, StreamingFactory}

object Job {
  def main(args: Array[String]) {
    val ssc = StreamingFactory.getStreamingContext

    val lines = ssc.socketTextStream("localhost", Resources.getStreamPort)

    val ds = lines.map(line => line.split(",").map(elem => elem.trim))

    ds.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
