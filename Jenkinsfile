pipeline {
    agent any 

     environment {
        AWS_ACCESS_KEY_ID     = credentials('jenkins-aws-secret-key-id')
        AWS_SECRET_ACCESS_KEY = credentials('jenkins-aws-secret-access-key')
    }      

    stages {
        stage('Publish') {
            steps {
                bat 'mvnw package'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                    bat 'aws configure set region us-east-1'
                    bat 'aws s3 cp ./target/jdbnk-policy-2.5.1.jar s3://jenkin-jars/jdbnk-policy-2.5.1.jar'
                }
            }
        }
    }
}
