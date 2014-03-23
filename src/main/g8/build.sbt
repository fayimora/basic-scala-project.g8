name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.10.3"

resolvers ++= Seq("cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0.M5b" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc(),
  "org.apache.spark" %% "spark-core" % "0.9.0-incubating" withSources() withJavadoc(),
  "org.apache.hadoop" % "hadoop-client" % "2.2.0-cdh5.0.0-beta-2" withJavadoc(),
  "com.github.scopt" %% "scopt" % "3.2.0"
)

initialCommands := "import $organization$.$name;format="lower,word"$._"

