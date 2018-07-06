pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
  }
}