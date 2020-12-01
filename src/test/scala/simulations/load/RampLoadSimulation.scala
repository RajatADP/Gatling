package simulations.load

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class RampLoadSimulation extends Simulation {

  val httpConf = http.baseUrl(
    "http://localhost:8080/app/")
    .header("Accept", "application/json")

  def getAllVideoGames() = {
    exec(http("Get all Video Games call")
      .get("videogames")
      .check(status.is(200)))
  }

  def getSpecificVideoGame() = {
    exec(http("Get specific Video Game call")
      .get("videogames/1")
      .check(status.is(200)))
  }

  val scn = scenario("Ramp Load simulation")
    .exec(getAllVideoGames())
    .pause(5)

    .exec(getSpecificVideoGame())
    .pause(5)

    .exec(getAllVideoGames())

  /* setUp(
     scn.inject(nothingFor(5),
       constantUsersPerSec(10) during(10 seconds))
   ).protocols(httpConf.inferHtmlResources())
 */
  setUp(
    scn.inject(nothingFor(5),
      rampUsersPerSec(1) to (5) during(10 seconds))
  ).protocols(httpConf.inferHtmlResources())
}