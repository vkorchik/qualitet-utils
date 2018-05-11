import sbtrelease.ReleaseStateTransformations._

def updateCredentials() = (for {
  username <- Option(System.getenv().get("SONATYPE_USERNAME"))
  password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
} yield
  credentials += Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password)
  ).getOrElse(credentials ++= Seq())
updateCredentials()

publishTo := sonatypePublishTo.value
sonatypeProfileName := organization.value

publishMavenStyle := true
publishArtifact in Test := false

crossScalaVersions := Seq("2.11.12", "2.12.5")

releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

import xerial.sbt.Sonatype._

sonatypeProjectHosting := Some(GitHubHosting("vkorchik", "qualitet-utils", "vagenius23@gmail.com"))

homepage := Some(url("https://github.com/vkorchik/qualitet-utils"))

scmInfo in ThisBuild := Some(
  ScmInfo(
    url("https://github.com/vkorchik/qualitet-utils"),
    "scm:git@github.com:vkorchik/qualitet-utils.git"
  )
)

developers in ThisBuild := List(
  Developer("vkorchik", "Vladimir KORCHIK", "vagenius23@gmail.com", url("https://github.com/vkorchik"))
)
//pomExtra in Global := {
//  <scm>
//    <connection>scm:git:github.com/vkorchik/qualitet-utils.git</connection>
//    <developerConnection>scm:git:git@github.com:vkorchik/qualitet-utils.git</developerConnection>
//    <url>github.com/vkorchik/qualitet-utils.git</url>
//  </scm>
//    <developers>
//      <developer>
//        <id>vkorchik</id>
//        <name>Vladimir KORCHIK</name>
//        <url>https://github.com/vkorchik</url>
//      </developer>
//    </developers>
//}

releaseCrossBuild := true
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommandAndRemaining("sonatypeReleaseAll")
)
