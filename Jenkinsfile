#!groovy

def label = "worker-${UUID.randomUUID().toString()}"
def deploystack = "java-sample"
def projectbase = "java-sample"
def registrybase = "/marcelferry/java-sample"
def registryserver = ""
def idcredentials = "git:a98dc49b395017291831c1b0c0ab55bc5a1cea666fa6b58c8f0eb35a3d316b79"
def channel = "integration"
def recipients = "me@marcelferry.com.br"
def submitters = "marcelferry"
 

node(label) {

	try {
	
		currentBuild.result = "SUCCESS"

        stage('Checkout') {
            checkout scm
            slackSend channel: channel, color: '#42e2f4', message: "Started - ${env.JOB_NAME} - ${env.BUILD_NUMBER}"
        }
        
        def maven = docker.image('maven:3.3.9-jdk-8-alpine')
        
        maven.inside(){ 
		
	        stage('Build and Test') {
                sh 'mvn clean install -DskipTests=true'
	        }
	        
	        stage('Unit Tests') {
	    		sh 'mvn test'
			}
			
			if (env.BRANCH_NAME == 'develop') {
				stage('Code Analysis') {
					sh 'mvn sonar:sonar'
				}
			}
	        
	       /*
	         Se projeto for de biblioteca
	       */
	       /* stage('Publish to Nexus') {
	    		sh 'mvn deploy -DskipTests=true'
			} */
		
		}
		
		if (env.BRANCH_NAME == 'homolog') {
		
			/* publicando imagem no registry do git usando docker instance - command line */
			stage('Build and Push Image') {
				def image = docker.build("${registrybase}:${env.BUILD_NUMBER}")
				docker.withRegistry(registryserver, idcredentials) {
					image.push()
					image.push('latest')
				}
			}
		
			/* publicando imagem no registry do git manualmente - command line */
			/* stage('Build and Push Image') {
				sh "docker build -t ${registrybase}:${env.BUILD_NUMBER} ."
				sh "docker tag ${registrybase}:${env.BUILD_NUMBER} {registrybase}:latest "
				withDockerRegistry([ credentialsId: idcredentials, url: registryserver ]) {
		          sh "docker push ${registrybase}:${env.BUILD_NUMBER}"
		          sh "docker push ${registrybase}:latest"
				}
			}*/
			
			stage('Remove local images') {
		        // remove docker images
		        sh("docker rmi -f ${registrybase}:latest || :")
		        sh("docker rmi -f ${registrybase}:$revision || :")
		        sh("docker rmi -f ${projectbase}:$revision || :")
		    }
					
		
			stage('Aprovacao - Homolog') {
				slackSend channel: channel, color: '#42e2f4', message: "Aprovar - ${env.JOB_NAME} - http://jenkins.devops.marcelferry.com.br/blue/organizations/jenkins/${projectbase}/detail/homolog/${env.BUILD_NUMBER}/pipeline/ " 
    		        
				try {
					input message: 'Essa versão está pronta para ir para Homolog ?', submitter: submitters
				
	        		rancher environmentId: "1a1367" , endpoint: "https://rancher.devops.marcelferry.com.br/v2-beta" , credentialId: "rancher-server" , service: "${deploystack}/${projectbase}" , image: "$registrybase:$revision"
	        			
				} catch(err) {
					slackSend channel: channel, color: '#d80f41', message: "Build (${env.BUILD_NUMBER}) from Project - ${env.JOB_NAME} - ABORTED in QA "
					error "Build (${env.BUILD_NUMBER}) from Project - ${env.JOB_NAME} - ABORTED in QA"
				}
			}
			
			stage('Aprovacao - Prod') {
				slackSend channel: channel, color: '#42e2f4', message: "Aprovar - ${env.JOB_NAME} - http://jenkins.devops.marcelferry.com.br/blue/organizations/jenkins/${projectbase}/detail/homolog/${env.BUILD_NUMBER}/pipeline/ " 
    		        
				try {
					input message: 'Essa versão está pronta para ir para Prod ?', submitter: submitters
				
	        		rancher environmentId: "1a8" , endpoint: "https://rancher.devops.marcelferry.com.br/v2-beta" , credentialId: "rancher-server" , service: "${deploystack}/${projectbase}" , image: "$registrybase:$revision"
	        			
				} catch(err) {
					slackSend channel: channel, color: '#d80f41', message: "Build (${env.BUILD_NUMBER}) from Project - ${env.JOB_NAME} - ABORTED in PRD "
					error "Build (${env.BUILD_NUMBER}) from Project - ${env.JOB_NAME} - ABORTED in PRD"
				}
			}
		}

    } catch (err) {
        currentBuild.result = "FAILED"
        echo "Failed: ${err}"
    } finally {
        echo currentBuild.result
        echo "Resultado: ${currentBuild.result}"
        notifyBuild(currentBuild.result)
    }
}
    
def notifyBuild(String buildStatus = 'STARTED') {
			  
	buildStatus =  buildStatus ?: 'SUCCESSFUL'
  
  	def colorName = 'RED'
  	def colorCode = '#FF0000'
  	def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  	def summary = "${subject} (${env.BUILD_URL})"
  	def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>"""
 
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESS') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else {
    color = 'RED'
    colorCode = '#FF0000'
  }

  slackSend (channel: channel, color: colorCode, message: summary)
 
  emailext (
      subject: subject,
      body: details,
      to: recepients
    )
} 