package application

import factories.StreamingFactory

object Job {
  def main(args: Array[String]) {
    val ssc = StreamingFactory.getStreamingContext

    val lines = ssc.socketTextStream("localhost", 7777)

  }
}
