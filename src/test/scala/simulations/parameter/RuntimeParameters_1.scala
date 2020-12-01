package simulations.parameter

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt
import io.gatling.core.scenario.Simulation

class RuntimeParameters_1 extends Simulation {

  val httpConf = http.baseUrl(
    "http://localhost:8080/app/")
    .header("Accept", "application/json")

  def getAllVideoGames() = {
    exec(http("Get all Video Games call")
      .get("videogames")
      .check(status.is(200)))
  }

  private def getProperty(propertyName: String, defaultValue: String) = {
    Option(System.getenv(propertyName))
      .orElse(Option(System.getProperty(propertyName)))
      .getOrElse(defaultValue)
  }

  val userCount: Int = getProperty("USERS", "5").toInt
  val rampDuration: Int = getProperty("RAMP_DURATION", "10").toInt
  val testDuration: Int = getProperty("DURATION", "60").toInt


  before {
    println(s"Running test with ${userCount} users")
    println(s"Ramping up users with ${rampDuration} seconds")
    println(s"Total test duration ${testDuration} seconds")
  }

  val scn = scenario("Basic Load simulation")
    .forever() {
      exec(getAllVideoGames())
    }


  setUp(
    scn.inject(nothingFor(5),
      rampUsers(userCount) during (rampDuration seconds))
  ).protocols(httpConf.inferHtmlResources()
  ).maxDuration(testDuration minute)
}