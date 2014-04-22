import AssemblyKeys._ // put this at the top of the file

assemblySettings

jarName in assembly := "$name;format="lower,hyphen"$.jar"

run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))
