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

publishMavenStyle := true
publishArtifact in Test := false

releaseCrossBuild := true
crossScalaVersions := Seq("2.11.8", "2.12.3")

releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

licenses := Seq("Mozilla Public License, version 2.0" -> url("https://www.mozilla.org/MPL/2.0/"))
homepage := Some(url("https://github.com/vkorchik/qualitet-utils"))

//scmInfo := Some(ScmInfo(
//  url("https://github.com/vkorchik/qualitet-utils"),
//  "scm:git@github.com/vkorchik/qualitet-utils.git"
//))
//
//developers := List(
//  Developer(
//    id    = "vkorchik",
//    name  = "Vladimir KORCHIK",
//    email = "vagenius23@gmail.com",
//    url   = url("https://github.com/vkorchik")
//  )
//)
//
//
pomExtra in Global := {
  <scm>
    <connection>scm:git:github.com/vkorchik/qualitet-utils.git</connection>
    <developerConnection>scm:git:git@github.com:vkorchik/qualitet-utils.git</developerConnection>
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
//  ,
//  pushChanges
)
