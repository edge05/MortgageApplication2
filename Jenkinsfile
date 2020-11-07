pipeline {
    agent { node { label 'zOS' } }

    options {
        timestamps()
    }
    
    environment {	
		groovyzHome			= '/opt/lpp/IBM/dbb/bin'
		DBB_HOME			= '/opt/lpp/IBM/dbb'
        DBB_CONF			= '/u/jerrye/conf'
        DBBLib				= '/opt/lpp/IBM/dbb/lib/*'
        polyJarFile			= '/u/jerrye/lib/polycephaly.jar'
        ibmjzosJar			= '/usr/lpp/java/J8.0_64/lib/ext/ibmjzos.jar'
        DBBcoreJar			= '/opt/lpp/IBM/dbb/lib/dbb.core_1.0.6.jar'
        DBBhtmlJar			= '/opt/lpp/IBM/dbb/lib/dbb.html_1.0.6.jar'
        polyClassPath		= "${env.polyJarFile}:${env.ibmjzosJar}:${env.DBBLib}"

    }

    stages {
        stage('Clean workspace') {
            steps {
                cleanWs()
            }
        }
	    stage ('Start') {
	      steps {
	        // send to email
	        emailext (
	            subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
	            body: """STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'>
	              Check console output at;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]""",
	            recipientProviders: [[$class: 'DevelopersRecipientProvider']]
	          )
	        }
	    }
    	stage("CheckOut")  {
    		steps {
    			checkout scm
    		}	 
		}
		stage("Build") {
            steps {
            	sh "export DBB_HOME=${env.DBB_HOME}"
            	sh "export DBB_CONF=${env.DBB_CONF}"
 				sh "${env.groovyzHome}/groovyz --classpath .:${env.polyClassPath} $WORKSPACE/build/build.groovy --collection MortgageApplication --sourceDir $WORKSPACE/conf/package.txt"           
            }
        }
        stage("Test") {
            options {
                timeout(time: 2, unit: "MINUTES")
            }
            steps {
                sh 'printf "\\Some tests execution here...\\e[0m\\n"'
            }
        }
        stage("Deploy") {
            options {
                timeout(time: 2, unit: "MINUTES")
            }
            steps {
                sh 'printf "\\e[31m Deploy package...\\e[0m\\n"'
            }
        }
    }
	post {
	    success {
	      emailext (
          		attachLog: true, attachmentsPattern: '*.log',
          		subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    			body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
     			recipientProviders: [developers(), requestor()],
	        )
	    }
	
	    failure {
	          emailext (
	          	attachLog: true, attachmentsPattern: '*.log',
    			body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
     			recipientProviders: [developers(), requestor()],
     			subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}"
	        )
	    }
    }
}
  