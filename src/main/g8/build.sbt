name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest_2.10" % "1.9.1" % "test"
)

initialCommands := "import $organization$.$name;format="lower,word"$._"

