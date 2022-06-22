pipeline {
    agent any

    environment {
            ANDROID_SDK_ROOT = 'c:\\users\\melanie.backers\\AppData\\Local\\Android\\Sdk'
            JAVA_HOME = 'c:\\Program Files\\Java\\jdk-11.0.15'
            GRADLE_USER_HOME = 'c:\\mel\\tools\\Gradle'
        }

    stages {
        stage('Build') {
            steps {
                echo 'building....'
                bat "gradlew widgets:clean billing:clean api:clean app:clean widgets:assembleDebug api:assembleDebug billing:assembleDebug app:assembleAmazonDebug"
            }
        }
        stage('Test') {
            steps {
                echo 'test'
                bat "set"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
        stage('SonarQube Analysis') {
             environment {
                 SONARSCANNER_HOME = tool 'SonarQube'
             }
             steps {
                  tool name: 'SonarQube', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                  withSonarQubeEnv('SonarQube') {
                      bat "set"
                      bat "${SONARSCANNER_HOME}/bin/sonar-scanner \
                      -D sonar.login=admin \
                      -D sonar.password=admin \
                      -D sonar.projectKey=ASV-SeriesGuide \
                      -D sonar.exclusions=**/*.java \
                      -D sonar.host.url=http://localhost:9000/"
                  }
             }
        }
    }
}