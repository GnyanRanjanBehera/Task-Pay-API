pipeline{
    agent any
    tools{
        maven "maven"
    }


    stages{

        stage("SCM checkout"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/GnyanRanjanBehera/Task-Pay-API.git']])
            }
        }

        stage("Build Process"){
            steps{
                script{
                    sh 'mvn clean install'
                }
            }
        }


        stage("Deploy to Container"){
                    steps{
                        deploy adapters: [tomcat9(alternativeDeploymentContext: '', credentialsId: 'tomcat-pwd', path: '', url: 'http://localhost:9090/')], contextPath: 'task_pay', war: '**/*.war'
                    }
                }
        }

    }


}