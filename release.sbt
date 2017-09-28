import sbtrelease.ReleaseStateTransformations._

//resolvers ++= Seq(
//  Resolver.sonatypeRepo("releases"),
//  Resolver.sonatypeRepo("snapshots"))


publishMavenStyle := true
publishArtifact in Test := false
//publishTo := {
//  val nexus = "https://oss.sonatype.org/"
//  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
//  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
//}

releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

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
  releaseStepCommandAndRemaining("sonatypeReleaseAll"),
  pushChanges
)

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
