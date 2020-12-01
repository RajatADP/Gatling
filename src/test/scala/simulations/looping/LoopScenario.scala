package simulations.looping

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class LoopScenario extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val scn = scenario("Video Game DB- Code Reuse Scenario")
    .exec(getAllVideoGames)
    .pause(5)

  def getAllVideoGames() = {
    repeat(2) {
      exec(http("Get all Video Games")
        .get("videogames")
        .check(status.is(200)))
    }
  }


  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)

}
