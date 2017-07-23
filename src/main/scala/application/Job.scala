package application

import factories.{Resources, StreamingFactory}

object Job {
  def main(args: Array[String]) {
    val ssc = StreamingFactory.getStreamingContext

    val lines = ssc.socketTextStream("localhost", Resources.getStreamPort)

    val tmp = lines.map(line => line.split(",").map(elem => elem.trim))

    tmp.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
