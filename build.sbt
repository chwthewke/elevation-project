import sbt._
import sbt.Keys._

// format: off
organization      in ThisBuild := "net.chwthewke"
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "2.11.12"
conflictManager   in ThisBuild := ConflictManager.strict
// format: on

enablePlugins( FormatPlugin, DependenciesPlugin )

val geotrellis = depsGroup( "org.locationtech.geotrellis", "1.2.1" )( "geotrellis-raster" )()

val `elevation` = project
  .settings(
    libraryDependencies ++= cats ++ scalatest ++ geotrellis,
    initialCommands in console +=
      Seq( "import net.chwthewke.elevation._", "import geotrellis.raster._" ).mkString( "\n" )
  )
  .enablePlugins( SbtBuildInfo, ScalacPlugin )

val `elevation-project` = project
  .in( file( "." ) )
  .aggregate( `elevation` )
