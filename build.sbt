import sbt._
import sbt.Keys._

// format: off
organization      in ThisBuild := "net.chwthewke"
scalaOrganization in ThisBuild := "org.scala-lang"
scalaVersion      in ThisBuild := "2.11.12"
conflictManager   in ThisBuild := ConflictManager.strict
// format: on

enablePlugins( FormatPlugin, ScalacPlugin )

val `elevation` = project
  .settings( libraryDependencies ++= scalatest )
  .enablePlugins( SbtBuildInfo )

val `elevation-project` = project
  .in( file( "." ) )
  .aggregate( `elevation` )
