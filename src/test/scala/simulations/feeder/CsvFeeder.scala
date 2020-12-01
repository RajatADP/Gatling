package simulations.feeder

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class CsvFeeder extends Simulation {
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val csvFeeder = csv("data/gameCsvFeeder.csv").circular;

  val scn = scenario("CSV Feeder Scenario")
    .exec(getSpecificVideoGame())

  def getSpecificVideoGame() = {
    repeat(5) {
      feed(csvFeeder)
        .exec(http("Getting gameId 1, 5 times")
        .get("videogames/${gameId}")
        .check(jsonPath("$.name").is("${gameName}")))
        .pause(1)

    }
  }

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
