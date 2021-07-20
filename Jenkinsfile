pipeline {
    agent any
    environment {
        AWS_DEFAULT_REGION="us-east-2"
        AWS_ACCESS_KEY_ID=credentials("AWS_ACCESS_KEY_ID")
        AWS_SECRET_ACCESS_KEY=credentials("AWS_SECRET_ACCESS_KEY")
        // PATH="$PATH:$HOME/dctlenv/bin/"
        def mvnHome = tool name: 'maven-3', type: 'maven'
        def mvnCMD = "${mvnHome}/bin/mvn"
        def dockerHome = tool 'docker'
        PATH = "${dockerHome}/bin:${env.PATH}"
        registry = "sundarbabu/pet" 
        registryCredential = 'dockerhub' 
        dockerImage = ''
     
    }
    parameters {
        choice(
            choices: ['main' , 'master'],
            description: '',
            name: 'BRANCH_NAME')
    }
    
    options {
        ansiColor('xterm')
    }

    stages {
      
        stage("Build") {
            steps {
                sh "${mvnCMD} clean install -DskipTests"
            }
        }

        stage("Unit Test") {
            steps {
                sh "${mvnCMD} test"
                junit 'target/surefire-reports/*.xml'
            }
        }

        // stage('Staticcode Aanalysis') {
        //     steps {
        //         withSonarQubeEnv('sonar') {
        //             sh '${mvnCMD} org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar' 
                    
        //         }
        //     }
        // }

        
        stage("Dockerization") {
            failFast true
            parallel {
        stage("Build Docker Image") {
            steps {
                script {
                    dockerImage = docker.build registry + ":v1.0.0"
                } 
            }
        }

        // stage("1-Docker Image Aanalysis") {
        //     steps {
        //         sh '/var/jenkins_home/dive sundarbabu/pet:"$BUILD_NUMBER"'
        //     } 
        // }

        // stage("2-Vulnerability Aanalysis") {
        //     steps {
        //         sh '/var/jenkins_home/grype sundarbabu/pet:"v1.0.0"'
        //     }
        // }

        // stage("3-SBOM Generation") {
        //     steps {
        //         sh '/var/jenkins_home/syft sundarbabu/pet:"v1.0.0"'
        //             }

                }

            }
        // }

        stage("Push Image to Registry") {
            steps {
                script {
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Cleaning up Registry') { 
            steps { 
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
                
    }
}
