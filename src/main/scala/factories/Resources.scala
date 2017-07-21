package factories

import com.typesafe.config.ConfigFactory

object Resources {
  private val config = ConfigFactory.load

  def getYear: Int = config.getInt("year")
  def getLevel: Int = config.getInt("job.level")
  def getTop: Int = config.getInt("job.top")
  def isRationMaleToFemale: Boolean = config.getBoolean("job.is-m-to-f-ratio")

  def getDbName: String = config.getString("db.name")

  def getCollMillionaires: String = config.getString("db.collections.millionaires")
  def getCollPopulation: String = config.getString("db.collections.population")
  def getCollRatio: String = config.getString("db.collections.ratio")
  def getCollTop: String = config.getString("db.collections.top")

  def getDbServer: String = config.getString("db.server")
  def getDbPort: Int = config.getInt("db.port")

  def getSparkLocalConfig: String = config.getString("spark.master-config.local")
  def getSparkCoreCountConfig: String = config.getString("spark.core-count")
  def getSparkMemoryConfig: String = config.getString("spark.master-config.memory")

  def getStreamPeriod: Int = config.getInt("streaming.period")
}
