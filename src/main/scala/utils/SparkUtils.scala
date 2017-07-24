package utils

import dao.City

object SparkUtils {
  def recentlyCities(newValues: Seq[City], oldState: Option[City]): Option[City] = {
    if (newValues.isEmpty && oldState.isEmpty)
      return None

    if (newValues.isEmpty)
      return Some(oldState.get)

    val recently = newValues.maxBy(_.year)
    val res = oldState match {
      case Some(city) => if (city.year > recently.year) { city } else { recently }
      case None => recently
    }
    Some(res)
  }

  def toCountries(events: Seq[City], current: Option[Iterable[City]]): Option[Iterable[City]] = {
    Some(events ++ current.getOrElse(Iterable.empty))
  }
}
