pipeline {
    agent any

    environment {
            ANDROID_SDK_ROOT = 'c:\\users\\fback\\AppData\\Local\\Android\\Sdk'
            JAVA_HOME = '/opt/java/openjdk'
            GRADLE_USER_HOME = '/var/jenkins_home/workspace'
        }

    stages {
        stage('Build') {
            steps {
                echo 'building....'
                sh "gradlew widgets:clean billing:clean api:clean app:clean widgets:assembleDebug api:assembleDebug billing:assembleDebug app:assemblePureDebug"
            }
        }
        stage('Test build') {
            steps {
                echo 'Test Build with Coverage'
                // bat "set"
                // bat "gradlew app:assembleAndroidTest"
                sh "gradlew widgets:generateDebugSources widgets:createMockableJar widgets:generateDebugAndroidTestSources widgets:compileDebugUnitTestSources widgets:compileDebugAndroidTestSources widgets:compileDebugSources billing:generateDebugSources billing:createMockableJar billing:generateDebugAndroidTestSources billing:compileDebugUnitTestSources billing:compileDebugAndroidTestSources billing:compileDebugSources app:generatePureDebugSources app:createMockableJar app:generatePureDebugAndroidTestSources app:compilePureDebugUnitTestSources app:compilePureDebugAndroidTestSources app:compilePureDebugSources api:generateDebugSources api:createMockableJar api:generateDebugAndroidTestSources api:compileDebugUnitTestSources api:compileDebugAndroidTestSources api:compileDebugSources"
            }
        }
        
        stage('Test') {
            steps {
                echo 'test'
                sh "gradlew app:testPureDebugUnitTest"
            }
        }
        
        stage('SonarQube Analysis') {
             environment {
                 SONARSCANNER_HOME = tool 'SonarQube'
             }
             steps {
                  tool name: 'SonarQube', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                  withSonarQubeEnv('SonarQube') {
                      sh "set"
                      sh "${SONARSCANNER_HOME}/bin/sonar-scanner \
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
