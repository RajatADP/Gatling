# Gatling Tutorial

**Prerequisite**
1. Start Video-Game aap
2. http://localhost:8080/swagger-ui/index.html#/Video_Games
3. https://jsonpath.com/ (JSON Path Expression)

**Installation**
1. git clone https://github.com/gatling/gatling-tutorial.git

**Run Recorder from IDE**
1. run Recorder.scala

**Run Recorder from Terminal**
1. cd /Users/rajatmishra/Downloads/Softwares/gatling-charts-highcharts-bundle-3.4.1/bin
2. ./recorder.sh

**Run Simulation from IDE**
1. run Engine.scala

**Run Simulation from Terminal**
1. cd /Users/rajatmishra/Downloads/Softwares/gatling-charts-highcharts-bundle-3.4.1/bin
2. ./gatling.sh
3. Select test to run and Enter

**Run Simulation from CI**
1. mvn gatling:test -Dgatling.simulationClass=simulations.RuntimeParameters
2. mvn gatling:test -Dgatling.simulationClass=simulations.RuntimeParameters_1 -DUSERS=5 -DRAMP_DURATION=5 -DDURATION=30
3. gradlew gatlingRun-simulations.RuntimeParameters

**References**

1. More on Debug
https://www.james-willett.com/debug-gatling/
2. Good Read
https://www.james-willett.com/
3. Cheat Sheet
https://gatling.io/docs/current/cheat-sheet/