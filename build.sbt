
scalaVersion := "2.12.8"

libraryDependencies ++= hai.build.deps

enablePlugins(JavaServerAppPackaging)
enablePlugins(DockerPlugin)
dockerBaseImage := "openjdk:8"


lazy val `agoda-svc` = project.in(file("."))

