pipeline {
    agent any

    stages {
        stage('git clone') {
            steps {
                git branch: 'develop', credentialsId: 'sunsuking', url: 'https://lab.ssafy.com/s11-webmobile2-sub2/S11P12C211.git'
                script {
                    dir('server') {
                        sh 'cp /home/ubuntu/source/secret/application-auth.properties src/main/resources'
                        sh 'cp /home/ubuntu/source/secret/application-prod.properties src/main/resources'
                    }
                }
            }
        }

        stage('docker run') {
            steps {
                dir('server') {
                    sh 'docker-compose up -d'
                }
            }
        }

        stage('test server') {
            steps {
                dir('server') {
                    withGradle {
                        sh './gradlew test'
                    }
                }
            }
        }

        stage('build server') {
            steps {
                dir('server') {
                    withGradle {
                        sh './gradlew clean build'
                    }
                }
            }
        }

        stage('deploy') {
            steps {
                dir('server') {
                    sh 'sudo cp build/libs/server-0.0.1-SNAPSHOT.jar /home/ubuntu/source/server-0.0.1-SNAPSHOT.jar'
                    sh 'sudo service ssapick-server restart'
                }
            }
        }
    }
}