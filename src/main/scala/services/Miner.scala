package services

class Miner {
  private def update(newValues: Seq[Double], runningPopulation: Option[Double]): Option[Double] = {
    val newPopulation = runningPopulation.getOrElse(0.0) + newValues.sum
    Some(newPopulation)
  }
}
