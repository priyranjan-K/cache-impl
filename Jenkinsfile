pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/priyranjan-K/cache-impl.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean build'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        //stage('Deploy') {
        //    steps {
        //        // Add your deployment steps here, e.g., using SCP, SSH, Docker, etc.
        //        sh 'scp build/libs/*.jar user@server:/path/to/deploy'
        //    }
        //}
    }
    post {
        success {
            echo 'Build and Deploy succeeded!'
        }
        failure {
            echo 'Build or Deploy failed!'
        }
    }
}