package utils

import dao.{City, PartOfPeople}
import org.apache.spark.streaming.dstream.DStream

object Converter {
  def linesToCities(csv: DStream[String]): DStream[City] = {
    val data = csv.map(line => {
      var tmp_line: String = line
      var pos_first_quotes = tmp_line.indexOf('"')
      while (pos_first_quotes >= 0) {
        val pos_comma = tmp_line.indexOf(",", pos_first_quotes)
        val pos_second_quotes = tmp_line.indexOf('"', pos_first_quotes + 1)
        if (pos_comma > pos_first_quotes && pos_comma < pos_second_quotes)
        tmp_line = tmp_line.substring(0, pos_comma - 1) +
        tmp_line.substring(pos_comma).replaceFirst(",", "")
        tmp_line = tmp_line.replaceFirst("\"", " ")
        tmp_line = tmp_line.replaceFirst("\"", " ")
        pos_first_quotes = tmp_line.indexOf('"')
      }
      tmp_line
        .split(",")
        .map(elem => elem.trim)
    })

    val res = data.map(line => {
      val country = line(0).replaceAll("\\.", " ").replaceAll("\"", "")
      val city_name = line(4).replaceAll("\\.", " ").replaceAll("\"", "")
      val year = line(1).toInt
      val population = line(9).toFloat // чтобы перевод был в Int, а не Long!
      val sex = PartOfPeople.strToChar(line(3))
      City(
        country = country,
        name = city_name,
        year = year,
        population = Math.round(population), // Округляем!
        sex = sex
      )
    })
    res
  }
}
