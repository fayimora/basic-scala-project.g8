name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.12.4"

scalacOptions := Seq(
  "-Xfatal-warnings", "-deprecation", "-feature", "-unchecked"
)

val scalazVersion = "7.1.0"

assemblyJarName in assembly := s"$name;format="Camel"$-\${version.value}"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.13.5" % "test" withSources() withJavadoc()

//  "org.scalaz" %% "scalaz-core" % scalazVersion,
//  "org.scalaz" %% "scalaz-effect" % scalazVersion,
//  "org.scalaz" %% "scalaz-typelevel" % scalazVersion,
//  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test",

//  "org.slf4j" % "slf4j-api" % "1.7.7",
//  "org.slf4j" % "slf4j-simple" % "1.7.7",

//  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
//  "io.spray" %%  "spray-json" % "1.2.6",
//  "info.folone" %% "poi-scala" % "0.9",
//  "org.scalafx" %% "scalafx" % "1.0.0-R8",
//  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.2",
//  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"

)

initialCommands := "import $organization$.$name;format="lower,word"$._"

initialCommands in console := "import scalaz._, Scalaz._"

unmanagedJars in Compile +=
  Attributed.blank(
    file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oD")

