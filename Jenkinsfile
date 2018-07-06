pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage("test") {
      steps {
        sh 'mvn test'
      }
      post {
        always {
          junit 'target/surfire-reports/*.xml'
        }
      }
    }
  }
}