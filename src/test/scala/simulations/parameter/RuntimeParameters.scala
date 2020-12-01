package simulations.parameter

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import io.gatling.core.scenario.Simulation

class RuntimeParameters extends Simulation{

  val httpConf = http.baseUrl(
    "http://localhost:8080/app/")
    .header("Accept", "application/json")

  def getAllVideoGames() = {
    exec(http("Get all Video Games call")
      .get("videogames")
      .check(status.is(200)))
  }

  val scn = scenario("Basic Load simulation")
    .forever() {
      exec(getAllVideoGames())
    }


  setUp(
    scn.inject(nothingFor(5),
      atOnceUsers(5),
      rampUsers(1) during(1 seconds))
  ).protocols(httpConf.inferHtmlResources()
  ).maxDuration(1 minute)
}
