package simulations.pauseTime

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class AddPauseTime extends Simulation {


  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")


  val scn = scenario("Video Game DB- Pause Time Scenario")
    .exec(http("Get all Video Games")
      .get("videogames"))
    .pause(10)

    .exec(http("Get specific Video Game")
      .get("videogames/1"))
    .pause(1, 20)

    .exec(http("Get all Video Games")
      .get("videogames"))
    .pause(3000.milliseconds)

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
