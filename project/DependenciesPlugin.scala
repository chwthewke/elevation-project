import sbt._
import sbt.Keys._

object DependenciesPlugin extends AutoPlugin {
  type Deps = Seq[ModuleID]

  def group( organization: String, version: String )( artifacts: String* )( testArtifacts: String* ): Seq[ModuleID] =
    artifacts.map( organization %% _ % version ) ++ testArtifacts.map( organization %% _ % version % "test" )

  object autoImport {
    type Deps = DependenciesPlugin.Deps

    def depsGroup( organization: String, version: String )( artifacts: String* )(
        testArtifacts: String* ): Seq[ModuleID] =
      DependenciesPlugin.group( organization, version )( artifacts: _* )( testArtifacts: _* )

    implicit def ToGroupOps( deps: Deps ): GroupOps = new GroupOps( deps )

    val kindProjector: Deps = Seq( compilerPlugin( "org.spire-math" %% "kind-projector" % "0.9.6" ) )
    val splain: Deps        = Seq( compilerPlugin( "io.tryp"        % "splain"          % "0.2.7" cross CrossVersion.patch ) )

    val catsVersion     = "1.1.0"
    val cats: Deps      = group( "org.typelevel", catsVersion )( "cats-core" )()
    val catsExtra: Deps = group( "org.typelevel", catsVersion )( "cats-free", "cats-macros" )()

    val mouse: Deps = group( "org.typelevel", "0.17" )( "mouse" )()

    val scalaArm: Deps = group( "com.jsuereth", "2.0" )( "scala-arm" )()
    val monocleVersion = "1.5.0-cats"
    val monocle: Deps =
      group( "com.github.julien-truffaut", monocleVersion )( "monocle-core", "monocle-macro" )()

    val circeVersion = "0.9.3"
    val circe: Deps =
      group( "io.circe", circeVersion )( "circe-core", "circe-generic", "circe-parser" )()
    val circeOptics = Seq( "io.circe" %% "circe-optics" % circeVersion )

    val akkaVersion           = "2.5.12"
    val akkaHttpVersion       = "10.1.1"
    val akkaHttpTestkit: Deps = Seq( "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion )

    val akkaHttp: Deps =
      group( "com.typesafe.akka", akkaVersion )( "akka-actor", "akka-stream" )() ++
        group( "com.typesafe.akka", akkaHttpVersion )( "akka-http" )( "akka-http-testkit" ) ++
        Seq( "de.heikoseeberger" %% "akka-http-circe" % "1.20.1" )

    val sslConfigCore   = Seq( "com.typesafe"        %% "ssl-config-core" % "0.2.2" )
    val reactiveStreams = Seq( "org.reactivestreams" % "reactive-streams" % "1.0.2" )

    val enumeratum: Deps =
      Seq( "com.beachape" %% "enumeratum" % "1.5.13", "com.beachape" %% "enumeratum-circe" % "1.5.17" )

    val shapeless: Deps = Seq( "com.chuusai" %% "shapeless" % "2.3.3" )

    val java8compat: Deps      = Seq( "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0" )
    val scalaLangModules: Deps = group( "org.scala-lang.modules", "1.0.6" )( "scala-parser-combinators", "scala-xml" )()

    val logging: Deps = Seq( "org.slf4j" % "slf4j-api" % "1.7.25",
                            "ch.qos.logback"             % "logback-classic" % "1.2.3",
                            "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.0" )

    val pureconfig: Deps = Seq( "com.typesafe" % "config" % "1.3.2", "com.github.pureconfig" %% "pureconfig" % "0.9.1" )

    val slickVersion       = "3.2.3"
    val slick: Deps        = group( "com.typesafe.slick", slickVersion )( "slick", "slick-hikaricp" )()
    val slickCodegen: Deps = Seq( "com.typesafe.slick" %% "slick-codegen" % slickVersion )

    val postgresql: Deps     = Seq( "org.postgresql" % "postgresql" % "9.4.1212" )
    val h2databaseMain: Deps = Seq( "com.h2database" % "h2" % "1.4.197" )
    val h2database: Deps     = h2databaseMain % "test"
    val flywayCore: Deps     = Seq( "org.flywaydb" % "flyway-core" % "5.0.7" )

    val univocity: Deps = Seq( "com.univocity" % "univocity-parsers" % "2.6.1" )

    val jose4j: Deps = Seq( "org.bitbucket.b_c" % "jose4j" % "0.6.3" )

    val scalatestMain: Deps = Seq( "org.scalatest" %% "scalatest" % "3.0.5" )
    val scalatest: Deps     = scalatestMain % "test"

    val scalacheckMain: Deps =
      Seq(
        "org.scalacheck"      %% "scalacheck"      % "1.13.4",
        "io.github.amrhassan" %% "scalacheck-cats" % "0.4.0"
      )
    val scalacheck: Deps = scalacheckMain % "test"

    val autoDiff: Deps =
      group( "fr.thomasdufour", "0.2.0" )()( "auto-diff-core",
                                            "auto-diff-generic",
                                            "auto-diff-scalatest",
                                            "auto-diff-enumeratum" )

    val gatlingVersion = "2.3.1"
    val gatling =
      Seq(
        "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion,
        "io.gatling"            % "gatling-test-framework"    % gatlingVersion
      ) % "test,it"

  }

  import autoImport._

  override def buildSettings: Seq[Def.Setting[_]] =
    dependencyOverrides in ThisBuild ++= Seq(
      "org.scala-lang" % "scala-library" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    ) ++
      cats ++
      catsExtra ++
      mouse ++
      scalaArm ++
      monocle ++
      circe ++
      akkaHttp ++
      sslConfigCore ++
      reactiveStreams ++
      enumeratum ++
      shapeless ++
      java8compat ++
      scalaLangModules ++
      logging ++
      pureconfig ++
      slick ++
      slickCodegen ++
      postgresql ++
      h2database ++
      flywayCore ++
      univocity ++
      jose4j ++
      scalatest ++
      scalacheck ++
      autoDiff ++
      gatling

  class GroupOps( val self: Seq[ModuleID] ) extends AnyVal {
    def exclude( org: String, name: String ): Seq[ModuleID] =
      self.map( _.exclude( org, name ) )

    def %( configurations: String ): Seq[ModuleID] =
      self.map( _ % configurations )

    def classifier( c: String ): Seq[ModuleID] =
      self.map( _ classifier c )
  }
}
