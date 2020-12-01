package simulations.response

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class CheckResponseCode extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val scn = scenario("Video Game DB- Check Response Code Scenario")
    .exec(http("Get all Video Games")
      .get("videogames")
      .check(status.is(200)))

    .exec(http("Get all Video Games")
      .get("videogames")
      .check(status.in(200, 210)))

    .exec(http("Get all Video Games")
      .get("videogames")
      .check(status.not(400), status.not(500)))

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpConf)
  )

}
