package factories

import com.mongodb.casbah.{MongoClient, MongoCollection, MongoDB}

object DatabaseFactory {
  private val SERVER = Resources.getDbServer
  private val PORT   = Resources.getDbPort
  private val DATABASE = Resources.getDbName

  private val mongoClient = MongoClient(SERVER, PORT)
  private val db = mongoClient(DATABASE)

  def getDatabase: MongoDB = db
  def getRatioCollection: MongoCollection = db(Resources.getCollRatio)
  def getTopCollection: MongoCollection = db(Resources.getCollTop)
  def getPopulationCollection: MongoCollection = db(Resources.getCollPopulation)
  def getMillionairesCollection: MongoCollection = db(Resources.getCollMillionaires)

  def closeConnection(): Unit = {
    mongoClient.close()
  }
}
