import sbt._
import sbt.Keys._
import sbtbuildinfo._
import sbtbuildinfo.BuildInfoKeys._
import sbtrelease.Version

object SbtBuildInfo extends AutoPlugin {
  override def requires: Plugins = super.requires && BuildInfoPlugin

  val shortVersion = SettingKey[String]( "short-version" )

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(
      buildInfoPackage := "buildinfo",
      buildInfoObject := "ElevationProject",
      shortVersion := shortenVersion( version.value ),
      buildInfoKeys := BuildInfoKey.ofN( name, version, shortVersion, scalaVersion )
    )

  val MajorMinor = raw"(\d+)\.(\d+).*".r

  private def shortenVersion( version: String ): String =
    version match {
      case MajorMinor( maj, min ) => s"$maj.$min"
      case _                      => throw new IllegalArgumentException( s"Could not parse version $version" )
    }

}
