package services

import dao.City
import org.apache.spark.rdd.RDD

object Miner {
  def getPopulation(data: RDD[(String, Iterable[City])]): RDD[(String, Double)] = {
    data
      .map(pair => {
        var value = 0.0
        for (city <- pair._2)
          value += city.population
        (pair._1, value)
      })
  }

  def getPopulation(events: Seq[Double], oldState: Option[Double]): Option[Double] = {
    Some(events.sum + oldState.getOrElse(0.0))
  }
}
