package simulations.response

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

class CheckResponseBodyExtract extends Simulation {

  val httpConf = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  val scn = scenario("Video Game DB- Check Response Body Scenario")
    .exec(http("Get all Video Games")
      .get("videogames")
      .check(jsonPath("$[1].name").is("Gran Turismo 3"))
      .check(bodyString.saveAs("responseBody")))

    // Print value of name received from $[1].name on console
    .exec { session => println(session); session }

    // Print whole body on console
    .exec { session => println(session("responseBody").as[String]); session }

  setUp(
    scn.inject(atOnceUsers(1))
      .protocols(httpConf)
  )

}
