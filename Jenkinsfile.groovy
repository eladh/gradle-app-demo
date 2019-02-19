
    node('jenkins-pipeline') {
        // Get Artifactory server instance, defined in the Artifactory Plugin administration page.
        def server = Artifactory.server "artifactory"
        // Create an Artifactory Gradle instance.
        def rtGradle = Artifactory.newGradleBuild()
        def buildInfo = Artifactory.newBuildInfo()

        stage('Clone sources') {
            git url: 'https://github.com/eladh/gradle-app-demo.git' ,credentialsId: 'github'
        }

        stage ('Artifactory configuration') {
            // Tool name from Jenkins configuration
            rtGradle.tool = "Gradle-4.7"
            // Set Artifactory repositories for dependencies resolution and artifacts deployment.
            rtGradle.deployer repo:'gradle-local', server: server
            rtGradle.resolver repo:'gradle-virtual', server: server
            rtGradle.usesPlugin = true // Artifactory plugin already defined in build script
            rtGradle.useWrapper = true
        }

        stage ('Gradle build') {
            rtGradle.run rootDir: ".", buildFile: 'build.gradle', tasks: 'clean build', buildInfo: buildInfo
            rtGradle.run rootDir: ".", buildFile: 'build.gradle', tasks: 'artifactoryPublish', buildInfo: buildInfo
        }

        stage ('Publish build info') {
            server.publishBuildInfo buildInfo
        }
    }
