node {
    // Get Artifactory server instance, defined in the Artifactory Plugin administration page.
    def server = Artifactory.server "artifactory-server"
    // Create an Artifactory Gradle instance.
    def rtGradle = Artifactory.newGradleBuild()
    def buildInfo = Artifactory.newBuildInfo()

    stage('Clone sources') {
        git url: 'http://35.202.14.162/elad/gradle-repo.git'
    }

    stage ('Artifactory configuration') {
        // Tool name from Jenkins configuration
        rtGradle.tool = "Gradle-4.6"
        // Set Artifactory repositories for dependencies resolution and artifacts deployment.
        rtGradle.deployer repo:'gradle-release-local', server: server
        rtGradle.resolver repo:'gradle-release', server: server
        rtGradle.usesPlugin = true // Artifactory plugin already defined in build script
    }

    stage ('Gradle build') {
        rtGradle.run rootDir: ".", buildFile: 'build.gradle', tasks: 'clean build', buildInfo: buildInfo
        rtGradle.run rootDir: ".", buildFile: 'build.gradle', tasks: 'artifactoryPublish', buildInfo: buildInfo

    }

    stage ('Publish build info') {
        server.publishBuildInfo buildInfo
    }
}