package simulations.feeder

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.util.Random

class CustomFeeder extends Simulation {

  val httpConf = http.baseUrl(
    "http://localhost:8080/app/")
    .header("Accept", "application/json")

  val idNumbers = (11 to 20).iterator
  val rnd = new Random()
  val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
  val now = LocalDate.now()

  def randomString(length: Int): String = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def randomDate
  (startDate: LocalDate, random: Random): String = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumbers.next(),
    "name" -> ("Game-" + randomString(5)),
    "releaseDate" -> randomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(6)),
    "rating" -> ("Rating-" + randomString(4))
  ))

  val scn = scenario("custom feeder check")
    .exec(postNewVideoGame())

  def postNewVideoGame() = {
    repeat(10) {
      feed(customFeeder)
        .exec(http("Post new Video Game")
          .post("videogames/")
          .body(StringBody("{" +
            "\n\t\"id\": ${gameId}," +
            "\n\t\"name\": \"${name}\"," +
            "\n\t\"releaseDate\": \"${releaseDate}\"," +
            "\n\t\"reviewScore\": \"${reviewScore}\"," +
            "\n\t\"category\": \"${category}\"," +
            "\n\t\"rating\": \"${rating}\"\n}"
          )).asJson
          .check(status.is(200)))
        .pause(1)
    }
  }


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}

/*
Below feeder is provided as custom
{
  "id": 0,
  "name": "string",
  "releaseDate": "2020-11-29T11:12:08.503Z",
  "reviewScore": 0,
  "category": "string",
  "rating": "string"
}*/
