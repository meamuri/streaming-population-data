package services

import com.mongodb.DBObject
import com.mongodb.casbah.MongoDB
import com.mongodb.casbah.commons.MongoDBObject
import org.apache.hadoop.fs.shell.Count
import org.apache.spark.rdd.RDD

class Keeper(val db: MongoDB, val generalInfo: String) {
  def saveMillionaires(data: RDD[(String, Count)]): Unit ={
    val coll = db("millionaires")
    coll.drop()
    data.collect()
      .foreach(x => coll.save(getDbObject(x._1, x._2, "millionaires_count")) )
  }

  private def getDbObject(country: String, info: Any, name: String): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += generalInfo -> country
    builder += name -> info
    builder.result
  }
}
