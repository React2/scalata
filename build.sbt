
name := "scalata"

organization := "io.react2"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype Releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "org.tsers.zeison" %% "zeison"         % "0.6.0",
  "com.typesafe"      % "config"         % "1.2.1",
  "org.scalatest"     % "scalatest_2.11" % "2.2.1"  % "test"
)

