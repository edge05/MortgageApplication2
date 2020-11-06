pipeline {
    agent { node { label 'zOS' } }

    options {
        timestamps()
    }
    
    environment {	
    	binDir				= 'bin'
    	classesDir			= 'classes'	
		srcJavaZosFile		= 'src/main/java/com/jenkins/zos/file'
		srcJavaZosUtil		= 'src/main/java/com/zos/java/utilities'
		srcZosResbiuld		= 'src/main/zOS/com.zos.resbuild'
		srcGroovyZosLang	= 'src/main/groovy/com/zos/language'
		srcGrovoyZosUtil	= 'src/main/groovy/com/zos/groovy/utilities'
		srcGroovyPrgUtil	= 'src/main/groovy/com/zos/program/utilities'
		javaHome			= '/usr/lpp/java/J8.0_64/bin'
		groovyHome			= '/u/jerrye/jenkins/groovy/bin'
		groovyzHome			= '/opt/lpp/IBM/dbb/bin'
		ibmjzos				= '/usr/lpp/java/J8.0_64/lib/ext/ibmjzos.jar'
		dbbcore				= '/opt/lpp/IBM/dbb/lib/dbb.core_1.0.6.jar'
		dbbhtml				= '/opt/lpp/IBM/dbb/lib/dbb.html_1.0.6.jar'
		dbbJNI 				= '/opt/lpp/IBM/dbb/lib/libDBB_JNI64.so'
		polycephalyJar		= "${env.binDir}/polycephaly.jar"
		javaClassPath		= "${env.ibmjzos}:${env.dbbcore}:${env.dbbhtml}"
		groovyClassPath		= "${env.javaClassPath}:${env.polycephalyJar}"
		groovyLibPath		= "/opt/lpp/IBM/dbb/lib/*:${env.dbbJNI}:${env.groovyClassPath}"
		polyRuntime			= '/u/jerrye'
		
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
    	stage("CheckOut")  {
    		steps {
    			checkout scm
    		}	 
		}
		stage("Build") {
            options {
                timeout(time: 2, unit: "MINUTES")
            }
            steps {
            	sh "export DBB_HOME=/opt/lpp/IBM/dbb"
            	sh "export DBB_CONF=${env.polyRuntime}/conf"
                sh "${env.groovyzHome}/groovyz --classpath .:${env.groovyLibPath}:${env.polyRuntime}/bin/${env.polycephalyJar} $WORKSPACE/build/build.groovy --collection MortgageApplication --sourceDir $WORKSPACE/conf/package.txt"
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
  