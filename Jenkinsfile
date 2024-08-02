pipeline {
    agent any
    
    tools {
        nodejs "node-18"
    }

    stages {
        
        stage('Notify Start') {
            steps {
                script {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    mattermostSend(
                        color: 'warning',
                        message: "빌드 시작: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}\n(<${env.BUILD_URL}|Details>)",
                        endpoint: 'https://meeting.ssafy.com/hooks/zjfzpr5tk7bwudgp8hwdrf96ur',
                        channel: 'Jenkins_Build_Result'
                    )
                }
            }
        }
        
        
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
                        sh 'sudo ./gradlew test'
                    }
                }
            }
        }
        
        stage('documentation') {
            steps {
                dir('server') {
                    withGradle {
                        sh 'docker ps -q --filter "name=swagger" | xargs -r docker stop'
                        sh 'docker ps -aq --filter "name=swagger" | xargs -r docker rm'
                        sh 'sudo ./gradlew openapi3-security-schemes'
                        sh 'sudo cp build/resources/test/docs/ssapick-api-docs.yaml /home/ubuntu/source/ssapick-api-docs.yaml'
                        sh 'docker run -p 5050:8080 -e SWAGGER_JSON=/app/swagger.yaml -e BASE_URL=/docs -v /home/ubuntu/source/ssapick-api-docs.yaml:/app/swagger.yaml -d --name=swagger swaggerapi/swagger-ui'
                    }
                }
            }
        }

        stage('build server') {
            steps {
                dir('server') {
                    withGradle {
                        sh 'sudo ./gradlew clean build'
                    }
                }
            }
        }

        stage('build client') {
            steps {
                dir('client') {
                    sh 'rm -rf node_modules'
                    sh 'npm install'
                    sh 'CI=false npm run build'
                    sh 'sudo rm -rf /home/ubuntu/source/build'
                    sh 'sudo mv build /home/ubuntu/source'
                }
            }
        }

        stage('deploy') {
            steps {
                dir('server') {
                    sh 'sudo cp build/libs/server-0.0.1-SNAPSHOT.jar /home/ubuntu/source/server-0.0.1-SNAPSHOT.jar'
                    sh 'sudo service ssapick-server restart'
                    sh 'sudo service nginx restart'
                }
            }
        }
    }
    post {
        success {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend(color: 'good',
                    message: "빌드 성공: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}\n(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/zjfzpr5tk7bwudgp8hwdrf96ur',
                    channel: 'Jenkins_Build_Result'
                        )
            }
        }
        failure {
            script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend(color: 'danger',
                     message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}\n(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/zjfzpr5tk7bwudgp8hwdrf96ur',
                    channel: 'Jenkins_Build_Result'
                        )
            }
        }
    }
}
