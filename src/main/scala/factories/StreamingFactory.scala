package factories

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingFactory {
  private val conf = new SparkConf()
    .setMaster(Resources.getSparkLocalConfig)
    .setAppName("streaming-population-data")
    .set("spark.cores.max", Resources.getSparkCoreCountConfig)
    .set("spark.executor.memory", Resources.getSparkMemoryConfig)

  private val ssc = new StreamingContext(conf, Seconds(Resources.getStreamPeriod))

  def getStreamingContext: StreamingContext = ssc

}
