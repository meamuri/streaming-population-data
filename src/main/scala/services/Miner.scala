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

  def getPopulation(newValues: Seq[Int], oldState: Option[Int]): Option[Int] = {
    Some(newValues.sum + oldState.getOrElse(0))
  }

  def getMillionMore(newValues: Seq[City], oldState: Option[Int]): Option[Int] = {
    Some(newValues.size + oldState.getOrElse(0))
  }
}
