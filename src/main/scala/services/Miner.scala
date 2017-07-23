package services

import org.apache.spark.streaming.dstream.DStream

class Miner {
  private def update(newValues: Seq[Double], runningPopulation: Option[Double]): Option[Double] = {
    val newPopulation = runningPopulation.getOrElse(0.0) + newValues.sum
    Some(newPopulation)
  }

  def calculatePopulation(stream: DStream[String]): Unit = {

  }
}
