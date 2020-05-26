name := "scala-playground"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "dev.zio" %% "zio" % "1.0.0-RC18-2"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

libraryDependencies += "org.scala-lang.modules" %% "scala-async" % "0.10.0"
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided

libraryDependencies += "com.netflix.rxjava" % "rxjava-scala" % "0.19.1"

unmanagedBase := baseDirectory.value / "lib"