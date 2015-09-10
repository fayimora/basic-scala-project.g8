This is a [giter8](https://github.com/n8han/giter8) template for generating a new scala project. It comes bundled with:

* `main` and `test` source directories
* [ScalaTest](http://www.scalatest.org/)
* [Scalacheck](http://www.scalacheck.org/)
* SBT configuration for `0.13.7`, `Scala 2.11.5`, and `ScalaTest 2.2.1` dependencies
* project `name`, `organization` and `version` customizable as variables

### usage:
Follow g8 [installation instructions](http://github.com/n8han/giter8/#installation)  
go to your favourite shell and enter  

    g8 edombowsky/basic-scala-project.g8
    cd [app-name]
    sbt compile

generate project files for your favourite IDE

    sbt eclipse
    sbt gen-idea    
