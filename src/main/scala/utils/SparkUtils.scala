package utils

import dao.City

object SparkUtils {
  def recentlyCities(newValues: Seq[City], oldState: Option[City]): Option[City] = {
    if (newValues.isEmpty)
      return Some(oldState.get)

    val inNewSet = newValues.maxBy(_.year)
    val res = oldState match {
            case Some(city) => if (city.year >= inNewSet.year) { city } else { inNewSet}
            case None => inNewSet
          }
    Some(res)
//    if (newValues.isEmpty && oldState.isEmpty)
//      return None
//
//    if (newValues.isEmpty)
//      return Some(oldState.get)
//
//    val recently = newValues.maxBy(_.year)
//    val res = oldState match {
//      case Some(city) => if (city.year > recently.year) { city } else { recently }
//      case None => recently
//    }
//    Some(res)
  }

}
