package services

import com.mongodb.DBObject
import com.mongodb.casbah.{MongoCollection, MongoDB}
import com.mongodb.casbah.commons.MongoDBObject
import org.apache.spark.rdd.RDD

object Keeper {

  def saveMillionaires(data: RDD[(String, Int)], coll: MongoCollection): Unit ={
    coll.drop()
    data.collect()
      .foreach(x => coll.save(getDbObject(x._1, x._2, "millionaires_count")) )
  }

  private def getDbObject(country: String, info: Any, name: String): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "country" -> country
    builder += name -> info
    builder.result
  }
}
