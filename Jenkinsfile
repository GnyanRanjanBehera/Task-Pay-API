pipeline {
    agent any
    tools {
        maven "maven"
    }
    environment{
               APP_NAME = "spring-docker-cicd"
               RELEASE_NO= "1.0.0"
               DOCKER_USER= "gnyandocker"
               IMAGE_NAME= "${DOCKER_USER}"+"/"+"${APP_NAME}"
               IMAGE_TAG= "${RELEASE_NO}-${BUILD_NUMBER}"
        }

    stages {
        stage("SCM checkout") {
            steps {
                git branch: 'main',
                    url: 'https://github.com/GnyanRanjanBehera/Task-Pay-API.git'
            }
        }

        stage("Build Process") {
            steps {
                bat 'mvn clean install'
            }
        }
        stage("Build Image"){
                    steps{
                        script{
                            sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
                        }
                    }
                }
         stage("Deploy Image to Hub"){
                    steps{
                        withCredentials([string(credentialsId: 'docker-hub', variable: 'docker-hub')]) {
                         bat 'docker login -u gnyandocker -p ${docker-hub}'
                         sh 'docker push ${IMAGE_NAME}:${IMAGE_TAG}'
                        }
                    }
                }







    }
}











