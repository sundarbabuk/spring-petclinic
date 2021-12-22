pipeline {
  agent any
  environment {
        AWS_DEFAULT_REGION="us-east-1"
        AWS_ACCESS_KEY_ID     = credentials('jenkins-aws-secret-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('jenkins-aws-secret-access-key')
    //     AWS_ACCESS_KEY_ID=credentials("AWS_ACCESS_KEY_ID")
    //     AWS_SECRET_ACCESS_KEY=credentials("AWS_SECRET_ACCESS_KEY")
        // PATH="$PATH:$HOME/dctlenv/bin/"
        def mvnHome = tool name: 'maven-3', type: 'maven'
        def mvnCMD = "${mvnHome}/bin/mvn"
    //     def scannerHome = tool 'sonar'
    //     JAVA_HOME='${scannerHome}/jdk'
        def dockerHome = tool 'docker'
        PATH = "${dockerHome}/bin:${env.PATH}"
        registry = "sundarbabu/petdemo" 
        registryCredential = 'dockerhub' 
        dockerImage = 'sundarbabu/petdemo:v3.0.0'
        def server = Artifactory.server 'artifactory'
        def rtDocker = Artifactory.docker server: server
        def ARTIFACTORY_DOCKER_REGISTRY = "devsecopsdemo.jfrog.io/default-docker-local"
        STACKHAWK_API_KEY = credentials("stackhawk-api-key")
        GITHUB_TOKEN=credentials('ghcr-token')
        IMAGE_NAME='sundarbabuk/spring-petclinic'
        IMAGE_VERSION='1.0-001'
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
    //          sh "mvn clean install -DskipTests"
            }
        }

    stage('build image') {
      steps {
        sh 'docker build -t $IMAGE_NAME:$IMAGE_VERSION .'
      }
    }

    stage('login to GHCR') {
      steps {
        sh 'echo $GITHUB_TOKEN_PSW | docker login ghcr.io -u $GITHUB_TOKEN_USR -p --password-stdin'
      }
    }

    stage('tag image') {
      steps {
        sh 'docker tag $IMAGE_NAME:$IMAGE_VERSION ghcr.io/$IMAGE_NAME:$IMAGE_VERSION'
      }
    }

    stage('push image') {
      steps {
        sh 'docker push ghcr.io/$IMAGE_NAME:$IMAGE_VERSION'
      }
    }
  }

  post {
    always {
      sh 'docker logout'
    }
  }
}
