pipeline {
    agent any
    tools {
        maven "maven"
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
                sh 'mvn clean install'
            }
        }

        stage("Deploy to Container") {
            steps {
                deploy adapters: [
                    tomcat9(
                        credentialsId: 'tomcat-pwd',
                        path: '',
                        url: 'http://localhost:9090/'
                    )
                ],
                contextPath: 'task_pay',
                war: 'target/*.war'
            }
        }
    }
}
