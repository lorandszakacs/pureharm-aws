/**
  * Copyright (c) 2019 BusyMachines
  *
  * See company homepage at: https://www.busymachines.com/
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
// format: off
addCommandAlias("useScala212", s"++${CompilerSettings.scala2_12}")
addCommandAlias("useScala213", s"++${CompilerSettings.scala2_13}")
addCommandAlias("useDotty",    s"++${CompilerSettings.dottyVersion}")

addCommandAlias("it",             "IntegrationTest / test")
addCommandAlias("recompile",      ";clean;compile;")
addCommandAlias("build",          ";compile;Test/compile")
addCommandAlias("rebuild",        ";clean;compile;Test/compile")
addCommandAlias("rebuild-update", ";clean;update;compile;Test/compile")
addCommandAlias("ci",             ";scalafmtCheck;rebuild-update;test")
addCommandAlias("ci-quick",       ";scalafmtCheck;build;test;it")
addCommandAlias("doLocal",        ";clean;update;compile;publishLocal")

addCommandAlias("cleanPublishSigned", ";recompile;publishSigned")
addCommandAlias("do212Release",       ";useScala212;cleanPublishSigned;sonatypeBundleRelease")
addCommandAlias("do213Release",       ";useScala213;cleanPublishSigned;sonatypeBundleRelease")

addCommandAlias("lint", ";scalafixEnable;rebuild;scalafix;scalafmtAll")
// format: on

//*****************************************************************************
//*****************************************************************************
//********************************* PROJECTS **********************************
//*****************************************************************************
//*****************************************************************************

lazy val root = Project(id = "pureharm-aws", base = file("."))
  .settings(PublishingSettings.noPublishSettings)
  .settings(Settings.commonSettings)
  .aggregate(
    `aws-core`,
    `aws-s3`,
    `aws-cloudfront`,
    `aws-logger`,
    `aws-sns`,
  )

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//++++++++++++++++++++++++++++++++++++ AWS ++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

lazy val `aws-core` = project
  .settings(PublishingSettings.sonatypeSettings)
  .settings(Settings.commonSettings)
  .settings(
    name := "pureharm-aws-core",
    libraryDependencies ++= Seq(
      pureharmCorePhantom.withDottyCompat(scalaVersion.value),
      pureharmCoreAnomaly.withDottyCompat(scalaVersion.value),
      pureharmEffectsCats.withDottyCompat(scalaVersion.value),
      pureharmConfig.withDottyCompat(scalaVersion.value),
      amazonRegionsV2.withDottyCompat(scalaVersion.value),
    ),
  )

//#############################################################################

lazy val `aws-s3` = project
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(PublishingSettings.sonatypeSettings)
  .settings(Settings.commonSettings)
  .settings(
    name := "pureharm-aws-s3",
    libraryDependencies ++= Seq(
      monixCatnap.withDottyCompat(scalaVersion.value),
      pureharmCoreAnomaly.withDottyCompat(scalaVersion.value),
      pureharmCorePhantom.withDottyCompat(scalaVersion.value),
      pureharmEffectsCats.withDottyCompat(scalaVersion.value),
      pureharmConfig.withDottyCompat(scalaVersion.value),
      amazonS3V2.withDottyCompat(scalaVersion.value),
      scalaTest.withDottyCompat(scalaVersion.value)      % ITT,
      log4cats.withDottyCompat(scalaVersion.value)       % ITT,
      logbackClassic.withDottyCompat(scalaVersion.value) % ITT,
    ),
  )
  .dependsOn(
    `aws-core`
  )

//#############################################################################

lazy val `aws-cloudfront` = project
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(PublishingSettings.sonatypeSettings)
  .settings(Settings.commonSettings)
  .settings(
    name := "pureharm-aws-cloudfront",
    libraryDependencies ++= Seq(
      pureharmCoreAnomaly.withDottyCompat(scalaVersion.value),
      pureharmCorePhantom.withDottyCompat(scalaVersion.value),
      pureharmEffectsCats.withDottyCompat(scalaVersion.value),
      pureharmConfig.withDottyCompat(scalaVersion.value),
      amazonCloudFront.withDottyCompat(scalaVersion.value),
      scalaTest.withDottyCompat(scalaVersion.value)      % ITT,
      log4cats.withDottyCompat(scalaVersion.value)       % ITT,
      http4sClient.withDottyCompat(scalaVersion.value)   % ITT,
      logbackClassic.withDottyCompat(scalaVersion.value) % ITT,
    ),
  )
  .dependsOn(
    `aws-core`,
    `aws-s3`,
  )

//#############################################################################

lazy val `aws-logger` = project
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(PublishingSettings.sonatypeSettings)
  .settings(Settings.commonSettings)
  .settings(
    name := "pureharm-aws-logger",
    libraryDependencies ++= Seq(
      pureharmCoreAnomaly.withDottyCompat(scalaVersion.value),
      pureharmCorePhantom.withDottyCompat(scalaVersion.value),
      pureharmEffectsCats.withDottyCompat(scalaVersion.value),
      pureharmConfig.withDottyCompat(scalaVersion.value),
      amazonLogs.withDottyCompat(scalaVersion.value),
      log4cats.withDottyCompat(scalaVersion.value),
      scalaTest.withDottyCompat(scalaVersion.value)      % ITT,
      logbackClassic.withDottyCompat(scalaVersion.value) % ITT,
    ),
  )
  .dependsOn(
    `aws-core`
  )

//#############################################################################

lazy val `aws-sns` = project
  .configs(IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(PublishingSettings.sonatypeSettings)
  .settings(Settings.commonSettings)
  .settings(
    name := "pureharm-aws-sns",
    libraryDependencies ++= Seq(
      pureharmCoreAnomaly.withDottyCompat(scalaVersion.value),
      pureharmCorePhantom.withDottyCompat(scalaVersion.value),
      pureharmEffectsCats.withDottyCompat(scalaVersion.value),
      pureharmConfig.withDottyCompat(scalaVersion.value),
      pureharmJsonCirce.withDottyCompat(scalaVersion.value),
      amazonSNSV2.withDottyCompat(scalaVersion.value),
      scalaTest.withDottyCompat(scalaVersion.value)      % ITT,
      log4cats.withDottyCompat(scalaVersion.value)       % ITT,
      http4sClient.withDottyCompat(scalaVersion.value)   % ITT,
      logbackClassic.withDottyCompat(scalaVersion.value) % ITT,
    ),
  )
  .dependsOn(
    `aws-core`
  )

//#############################################################################
//#############################################################################
//################################ DEPENDENCIES ###############################
//#############################################################################
//#############################################################################

lazy val pureharmVersion:        String = "0.0.6-M4" //https://github.com/busymachines/pureharm/releases
lazy val scalaCollCompatVersion: String = "2.1.6"    //https://github.com/scala/scala-collection-compat/releases
lazy val monixVersion:           String = "3.2.2"    //https://github.com/monix/monix/releases
lazy val log4catsVersion:        String = "1.1.1"    //https://github.com/ChristopherDavenport/log4cats/releases
lazy val awsJavaSdkVersion:      String = "1.11.822" //java — https://github.com/aws/aws-sdk-java/releases
lazy val awsJavaSdkV2Version:    String = "2.13.57"  //java — https://github.com/aws/aws-sdk-java-v2/releases

//these are used only for testing
lazy val logbackVersion:   String = "1.2.3"  //https://github.com/qos-ch/logback/releases
lazy val http4sVersion:    String = "0.21.6" //https://github.com/http4s/http4s/releases
lazy val scalaTestVersion: String = "3.2.0"  //https://github.com/scalatest/scalatest/releases

//#############################################################################
//################################### SCALA ###################################
//#############################################################################

//https://github.com/scala/scala-collection-compat/releases
lazy val scalaCollectionCompat: ModuleID =
  "org.scala-lang.modules" %% "scala-collection-compat" % scalaCollCompatVersion withSources ()

//#############################################################################
//################################# PUREHARM ##################################
//#############################################################################

//https://github.com/busymachines/pureharm/releases/
def pureharm(m: String): ModuleID = ("com.busymachines" %% s"pureharm-$m" % pureharmVersion) withSources ()

lazy val pureharmCore:             ModuleID = pureharm("core")
lazy val pureharmCoreAnomaly:      ModuleID = pureharm("core-anomaly")
lazy val pureharmCorePhantom:      ModuleID = pureharm("core-phantom")
lazy val pureharmCoreIdentifiable: ModuleID = pureharm("core-identifiable")
lazy val pureharmEffectsCats:      ModuleID = pureharm("effects-cats")
lazy val pureharmJsonCirce:        ModuleID = pureharm("json-circe")
lazy val pureharmConfig:           ModuleID = pureharm("config")

//#############################################################################
//################################# TYPELEVEL #################################
//#############################################################################

//https://github.com/monix/monix/releases
//we use this to interop with Java Futures from AWS stuff
lazy val monixCatnap: ModuleID = "io.monix" %% "monix-catnap" % monixVersion withSources ()

//used only for testing
//https://github.com/http4s/http4s/releases
lazy val http4sClient: ModuleID = "org.http4s" %% "http4s-blaze-client" % http4sVersion withSources ()

//#############################################################################
//################################ AMAZON V1 — ################################
//#############################################################################

//https://github.com/aws/aws-sdk-java/releases
lazy val amazonCloudFront = "com.amazonaws" % "aws-java-sdk-cloudfront" % awsJavaSdkVersion withSources ()
lazy val amazonLogs       = "com.amazonaws" % "aws-java-sdk-logs"       % awsJavaSdkVersion withSources ()

//#############################################################################
//################################  AMAZON V2 ################################
//#############################################################################

//https://github.com/aws/aws-sdk-java-v2/releases
lazy val amazonRegionsV2 = "software.amazon.awssdk" % "regions" % awsJavaSdkV2Version withSources ()
/**
  * currently, pretty much only S3 is usable, cloudfront and logs lack some serious features:
  * - cloudfront: cannot sign
  * - logs: uses shitty interop w/ slf4j. Way too much magic...
  */
lazy val amazonS3V2      = "software.amazon.awssdk" % "s3"      % awsJavaSdkV2Version withSources ()
lazy val amazonSNSV2     = "software.amazon.awssdk" % "sns"     % awsJavaSdkV2Version withSources ()

//#############################################################################
//################################## TESTING ##################################
//#############################################################################

//https://github.com/scalatest/scalatest/releases
lazy val scalaTest: ModuleID = "org.scalatest" %% "scalatest" % scalaTestVersion withSources ()

//#############################################################################
//#################################  LOGGING ##################################
//#############################################################################

//https://github.com/ChristopherDavenport/log4cats/releases
lazy val log4cats = "io.chrisdavenport" %% "log4cats-slf4j" % log4catsVersion withSources ()

//https://github.com/qos-ch/logback/releases — it is the backend implementation used by log4cats-slf4j
lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion withSources ()

//#############################################################################
//################################  BUILD UTILS ###############################
//#############################################################################
/**
  * See SBT docs:
  * https://www.scala-sbt.org/release/docs/Multi-Project.html#Per-configuration+classpath+dependencies
  *
  * Ensures dependencies between the ``test`` parts of the modules
  */
def fullDependency(p: Project): ClasspathDependency = p % "compile->compile;test->test"

/**
  * Used only when one module is useful to test another module, but
  * in production build they don't require to be used together.
  */
def asTestingDependency(p: Project): ClasspathDependency = p % "test -> compile"

/**
  * Used to mark a dependency as needed in both integration tests, and tests
  */
def ITT: String = "it,test"
