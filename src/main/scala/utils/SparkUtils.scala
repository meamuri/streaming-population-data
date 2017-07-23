package utils

import dao.City

object SparkUtils {
  def recentlyCities(events: Seq[City], current: Option[City]): Option[City] = {
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

  def toCountries(events: Seq[City], current: Option[Iterable[City]]): Option[Iterable[City]] = {
    Some(events ++ current.getOrElse(Iterable.empty))
  }
}
