package services

import dao.City

object Miner {
  def getPopulation(newValues: Seq[Int], oldState: Option[Int]): Option[Int] = {
    Some(newValues.sum + oldState.getOrElse(0))
  }

  def getCount(newValues: Seq[City], oldState: Option[Int]): Option[Int] = {
    Some(newValues.size + oldState.getOrElse(0))
  }

  def getTop(newValues: Seq[City], oldState: Option[Iterable[City]]): Option[Iterable[City]] = {
    Some((newValues ++ oldState.getOrElse(List())).toList.sortBy(_.population).reverse.take(5))
  }
}
