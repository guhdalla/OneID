# OneID
Projeto Challenger Plusoft by: NewGen

1 - Clonar o projeto </br>
2 - Subir a parte desevolvida na branch própria </br>
3 - Depois de validado subir na master </br>

Pipeline
```
pipeline {
    agent any

    tools {
        maven "apache-maven"
    }

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/guhdalla/OneID.git'
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                success {
                    deploy adapters: [tomcat9(credentialsId: '6303c914-1968-40f5-813e-c7090f725fb5', path: '', url: 'http://localhost:8080/')], contextPath: 'OneID', war: '**/*.war'
                }
            }
        }
    }
}
```
