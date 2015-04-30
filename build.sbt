
name := "scalata"

organization := "io.react2"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype Releases"  at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Scalaz Releases"    at "https://dl.bintray.com/scalaz/releases"
)

libraryDependencies ++= Seq(
  "org.tsers.zeison"  %% "zeison"            % "0.6.0",
  "org.scalaz.stream" %% "scalaz-stream"     % "0.7",
  "com.typesafe"      %  "config"            % "1.2.1",
  "org.mongodb"       %  "mongo-java-driver" % "3.0.0",
  "org.scalatest"     %  "scalatest_2.11"    % "2.2.1"  % "test"
)
