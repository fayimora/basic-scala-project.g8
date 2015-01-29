name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.1" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.12.1" % "test" withSources() withJavadoc()
)

initialCommands := "import $organization$.$name;format="lower,word"$._"

