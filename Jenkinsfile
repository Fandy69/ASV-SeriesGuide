pipeline {
    agent any

    environment {
            ANDROID_SDK_ROOT = 'C:\\Users\\fback\\AppData\\Local\\Android\\Sdk'
            JAVA_HOME = 'C:\\Program Files\\Microsoft\\jdk-11.0.12.7-hotspot'
            GRADLE_USER_HOME = 'C:\\Gradle'
        }

    stages {
        stage('Build Debug') {
            steps {
                echo 'Clean UP'
                bat "gradlew widgets:clean billing:clean api:clean app:clean Custclean"
                echo 'Build Pure Debug'
                bat "gradlew widgets:assembleDebug api:assembleDebug billing:assembleDebug app:assemblePureDebug"
            }
        }
        stage('Build Test') {
            steps {
                echo 'Build Pure Test with Coverage'
                bat "set"
                bat "gradlew widgets:generateDebugSources widgets:createMockableJar widgets:generateDebugAndroidTestSources \
                     widgets:compileDebugUnitTestSources widgets:compileDebugAndroidTestSources widgets:compileDebugSources \
                     billing:generateDebugSources billing:createMockableJar billing:generateDebugAndroidTestSources \
                     billing:compileDebugUnitTestSources billing:compileDebugAndroidTestSources billing:compileDebugSources \
                     app:generatePureDebugSources app:createMockableJar app:generatePureDebugAndroidTestSources \
                     app:compilePureDebugUnitTestSources app:compilePureDebugAndroidTestSources app:compilePureDebugSources \
                     api:generateDebugSources api:createMockableJar api:generateDebugAndroidTestSources \
                     api:compileDebugUnitTestSources api:compileDebugAndroidTestSources api:compileDebugSources"
            }
        }
        
//         stage('Test') {
//             steps {
//                 echo 'Test Pure'
//                 bat "gradlew app:testPureDebugUnitTest"
//             }
//         }

        
        stage('Test Pure Coverage reports') {
            steps {
//                junit '*/build/test-results/testPureDebugUnitTest/*.xml'
//                junit '**/build/test-results/**/*.xml'
                

//                 jacoco(
//                     execPattern: '**/build/jacoco/**.exec'
//                 )
                bat "gradlew app:testPureDebugUnitTest jacocoTestReport --info"
                step( 
                        //publishCoverage(adapters: [jacocoAdapter('build/reports/jacoco/test/jacocoTestReport.xml')] 
                        publishCoverage(adapters: [jacocoAdapter('target/site/jacoco/jacoco.xml')]        
                    )
                )
            }
        }


        
    
        
//         stage('Test with Coverage') {
//             steps {
//                 echo 'Test Build with Coverage'
//                 jacoco(
//                     execPattern: '**/build/jacoco/**.exec',
//                     classPattern: '**/classes/*/main'
//                 )                
//                 bat "gradlew app:testPureDebugUnitTest jacocoTestReport"
//             }
//         }        

//         stage('Publish Coverage') {
//             steps {
//                 echo 'Publish Coverage'
//                 publishCoverage(
//                     adapters: [jacocoAdapter('**/build/reports/jacoco/jacocoTestReport.xml')] )
                
//                 //bat "gradlew widgets:generateDebugSources widgets:createMockableJar widgets:generateDebugAndroidTestSources widgets:compileDebugUnitTestSources widgets:compileDebugAndroidTestSources widgets:compileDebugSources billing:generateDebugSources billing:createMockableJar billing:generateDebugAndroidTestSources billing:compileDebugUnitTestSources billing:compileDebugAndroidTestSources billing:compileDebugSources app:generatePureDebugSources app:createMockableJar app:generatePureDebugAndroidTestSources app:compilePureDebugUnitTestSources app:compilePureDebugAndroidTestSources app:compilePureDebugSources api:generateDebugSources api:createMockableJar api:generateDebugAndroidTestSources api:compileDebugUnitTestSources api:compileDebugAndroidTestSources api:compileDebugSources"
//             }
//         }
        
//         stage('Test Coverage') {
//             steps {
//                 echo 'Test Build with Coverage'
//                 junit '**/build/test-results/**/*.xml'
//                 jacoco(
//                     execPattern: '**/build/jacoco/**.exec'
//                 )
//                 bat "gradlew app:testPureDebugUnitTest jacocoTestReport"   // testCoverage Gaat fout bij Task :jacocoTestReport SKIPPED

//                 publishCoverage(
//                     adapters: [jacocoAdapter('**/build/reports/jacoco/jacocoTestReport.xml')] )
//             }
//         }
        
//         stage('SonarQube Analysis') {
//              environment {
//                  SONARSCANNER_HOME = tool 'SonarQube'
//              }
//              steps {
//                   tool name: 'SonarQube', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
//                   withSonarQubeEnv('SonarQube') {
//                       bat "set"
//                       bat "${SONARSCANNER_HOME}/bin/sonar-scanner \
//                       -D sonar.login=sqp_de5218ed22994034126ce0a159c0adef541cc102 \
//                       -D sonar.projectKey=ASV-SeriesGuide \
//                       -D sonar.java.binaries=** \
//                       -D sonar.host.url=http://192.168.2.86:9000/"
//                   }
//              }
//         }
        
//         stage('Sonar Quality Gate') {
//             steps {
//                 timeout(time: 2, unit: 'MINUTES') {
//                     waitForQualityGate abortPipeline: true
//                 }
//             }
//         }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }        
        
    }
    
//   post {
//     always {
//       junit(testResults: '**/build/test-results/**/*.xml', allowEmptyResults : true)
//     }
//   }    
}

