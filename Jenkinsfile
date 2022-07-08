pipeline {
    agent any

    environment {
            ANDROID_SDK_ROOT = 'C:\\Users\\fback\\AppData\\Local\\Android\\Sdk'
            JAVA_HOME = 'C:\\Program Files\\Microsoft\\jdk-11.0.12.7-hotspot'
            GRADLE_USER_HOME = 'C:\\Gradle'
        }

    stages {

        stage('Test') {
            steps {
                echo 'test'
                bat "gradlew app:testPureDebugUnitTest"
            }
        }        

        stage('Test build') {
            steps {
                echo 'Test Build with Coverage'
                bat "set"
                bat "gradlew widgets:generateDebugSources widgets:createMockableJar widgets:generateDebugAndroidTestSources widgets:compileDebugUnitTestSources widgets:compileDebugAndroidTestSources widgets:compileDebugSources billing:generateDebugSources billing:createMockableJar billing:generateDebugAndroidTestSources billing:compileDebugUnitTestSources billing:compileDebugAndroidTestSources billing:compileDebugSources app:generatePureDebugSources app:createMockableJar app:generatePureDebugAndroidTestSources app:compilePureDebugUnitTestSources app:compilePureDebugAndroidTestSources app:compilePureDebugSources api:generateDebugSources api:createMockableJar api:generateDebugAndroidTestSources api:compileDebugUnitTestSources api:compileDebugAndroidTestSources api:compileDebugSources"
            }
        }
        
        stage('Test Coverage') {
            steps {
                junit '**/build/test-results/**/*.xml'
                jacoco()

                publishCoverage(
                    adapters: [jacocoAdapter('build/reports/jacoco/test/jacocoTestReport.xml')] )
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
                      -D sonar.login=sqp_de5218ed22994034126ce0a159c0adef541cc102 \
                      -D sonar.projectKey=ASV-SeriesGuide \
                      -D sonar.java.binaries=** \
                      -D sonar.host.url=http://192.168.2.86:9000/"
                  }
             }
        }
        
        stage('Sonar Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }        
        
    }
    
  post {
    always {
      junit(testResults: 'build/test-results/*.xml', allowEmptyResults : true)
    }
  }    
    
}
