enablePlugins(ScalaJSPlugin)

name := "pleiad"
scalaVersion := "3.2.1"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "com.raquo" %%% "laminar" % "15.0.0-M1",
  "com.raquo" %%% "waypoint" % "6.0.0-M1"
)
