
lazy val commonSettings = Seq(
    scalaVersion := "2.12.3"
  )

lazy val librarySettings = Seq(
  "org.specs2"     %% "specs2-core"       % "3.8.6"  % Test,
  "org.specs2"     %% "specs2-scalacheck" % "3.8.6"  % Test,
  "org.scalacheck" %% "scalacheck"        % "1.13.4" % Test
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(name := "terra-frame")
  .settings(libraryDependencies ++= librarySettings)
  .settings(scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint"))
  .settings(scalacOptions in Test ++= Seq("-Yrangepos", "-Xlint"))
