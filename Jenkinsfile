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

        warnings(canComputeNew: false, categoriesPattern: '', consoleParsers: [[parserName: 'Maven'], [parserName: 'Java Compiler (javac)']], defaultEncoding: 'UTF-8', excludePattern: '', healthy: '', includePattern: '', messagesPattern: '', shouldDetectModules: true, unHealthy: '')
      }
    }
    stage('test') {
      post {
        always {
          junit 'target/surefire-reports/*.xml'
          findbugs canComputeNew: false, defaultEncoding: 'UTF-8', excludePattern: '', healthy: '', includePattern: '', pattern: 'target/findbugsXml.xml', unHealthy: ''
          pmd canComputeNew: false, defaultEncoding: 'UTF-8', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
          checkstyle canComputeNew: false, defaultEncoding: 'UTF-8', healthy: '', pattern: 'target/checkstyle-result.xml', unHealthy: ''
        }

      }
      steps {
        sh '''mkdir lib
cp /opt/data/lib/bukkit-1.12.2.jar ./lib/bukkit-1.12.2.jar'''
        sh 'mvn site'
      }
    }
  }
}