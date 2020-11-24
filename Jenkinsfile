pipeline {
    agent { node { label 'zOS' } }

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
        CollectionName		= 'MortgageApplication'
        projectClean		= 'true'
        DBBClean			= 'false'
        projectDelete		= 'false'

    }

    stages {
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
        stage('Clean workspace') {
            when {
            	expression {
                	env.projectClean.toBoolean()
           		}
        	}
            steps {
            	sh 'printf "running conditional clean of workspace"'
                cleanWs()
            }
        }
    	stage("CheckOut")  {
    		steps {
    			checkout scm
    		}
		}
        stage('DBB clean collection') {
            when {
            	expression {
                	env.DBBClean.toBoolean()
           		}
        	}
            steps {
            	sh 'printf "running DBB delete collection"'
            	sh "export DBB_HOME=${env.DBB_HOME}"
            	sh "export DBB_CONF=${env.DBB_CONF}"
            	sh "${env.groovyzHome}/groovyz --classpath .:${env.polyClassPath} $WORKSPACE/build/build.groovy --clean --collection ${env.CollectionName}"
            }
        }
		stage("Build") {
            steps {
            	sh "export DBB_HOME=${env.DBB_HOME}"
            	sh "export DBB_CONF=${env.DBB_CONF}"
 				sh "${env.groovyzHome}/groovyz --classpath .:${env.polyClassPath} $WORKSPACE/build/build.groovy --debug --collection ${env.CollectionName}"
            }
        }
        stage("Test") {
            steps {
                sh 'printf "\\Some tests execution here...\\e[0m\\n"'
            }
        }
        stage("Deploy") {
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
