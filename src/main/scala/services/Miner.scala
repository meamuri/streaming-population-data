package services

import dao.City
import org.apache.spark.streaming.dstream.DStream

object Miner {
  def update(events: Seq[City], current: Option[City]): Option[City] = {
    if (events.isEmpty && current.isEmpty)
      return None

    if (events.isEmpty)
      return Some(current.get)

    val recently = events.maxBy(_.year)
    val res = current match {
      case Some(city) => if (city.year > recently.year) { city } else { recently }
      case None => recently
    }
    Some(res)
  }

  def calculatePopulation(stream: DStream[String]): Unit = {

  }
}
