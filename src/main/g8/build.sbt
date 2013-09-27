name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "1.9.2" % "test"
)

initialCommands := "import $organization$.$name;format="lower,word"$._"

