package services

import dao.City

object Miner {
  def getPopulation(newValues: Seq[Int], oldState: Option[Int]): Option[Int] = {
    Some(newValues.sum)
  }

  def getCount(newValues: Seq[City], oldState: Option[Int]): Option[Int] = {
    Some(newValues.size)
  }

  def getTop(newValues: Seq[City], oldState: Option[Iterable[City]]): Option[Iterable[City]] = {
    Some(newValues.toList.sortBy(_.population).reverse.take(5))
  }
}
