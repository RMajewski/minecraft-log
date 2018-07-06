pipeline {
  agent any
  stages {
    stage('build') {
      agent any
      steps {
        sh '''mkdir lib
cp /opt/data/lib/bukkit-1.12.2.jar ./lib/bukkit-1.12.2.jar'''
        fileExists './lib/bukkit-1.12.2.jar'
        ansiColor(colorMapName: 'xterm') {
          sh 'mvn -B -DskipTests clean package'
        }

        warnings(useStableBuildAsReference: true, useDeltaValues: true)
      }
    }
    stage('test') {
      post {
        always {
          junit 'target/surfire-reports/*.xml'

        }

      }
      steps {
        sh 'mvn test'
      }
    }
  }
}