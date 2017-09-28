import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

name := "qualitet-utils"
version := "0.0.1"
scalaVersion := "2.12.3"

organization := "com.github.vkorchik"

licenses := Seq("Mozilla Public License, version 2.0" -> url("https://www.mozilla.org/MPL/2.0/"))

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"))


publishMavenStyle := true
publishArtifact in Test := false
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("+test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommandAndRemaining("sonatypeReleasesAll"),
  pushChanges
)

//useGpg := true

TravisCredentials.updateCredentials()

pomExtra in Global := {
  <scm>
    <connection>scm:git:github.com/vkorchik/qualitet-utils.git</connection>
    <developerConnection>scm:git:git@github.com:vkrochik/qualitet-utils.git</developerConnection>
    <url>github.com/vkorchik/qualitet-utils.git</url>
  </scm>
    <developers>
      <developer>
        <id>vkorchik</id>
        <name>Vladimir KORCHIK</name>
        <url>https://github.com/vkorchik</url>
      </developer>
    </developers>
}
