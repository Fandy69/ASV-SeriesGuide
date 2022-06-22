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
    }
}