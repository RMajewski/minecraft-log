pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage {
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