package simulations.feeder

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class Feeder extends Simulation {
  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val idNumbers = (1 to 10).iterator

  val feeder = Iterator.continually(Map("gameId" -> idNumbers.next()))

  val scn = scenario("Custom Feeder Scenario")
    .exec(getSpecificVideoGame())

  def getSpecificVideoGame() = {
    repeat(10) {
      feed(feeder)
        .exec(http("Getting specific game with gameId 1 to 10")
          .get("videogames/${gameId}")
          .check(status.is(200)))
        .pause(1)

    }
  }

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
