pipeline {
    agent any

    environment {
        // отдельные креды для логина/пароля
        LOGIN    = credentials('LOGIN')
        PASSWORD = credentials('PASSWORD')
        // username + password вместе
        SELENOID = credentials('selenoid-cred')
    }

    stages {
        stage('Load Env') {
            steps {
                script {
                    // Загружаем переменные из env.properties
                    def props = readProperties file: 'env.properties'
                    props.each { k, v ->
                        env[k] = v
                    }
                }
            }
        }

        stage('Test') {
            steps {
                sh """
                ./gradlew clean \${TASK} \\
                  -Dlogin=\${LOGIN} \\
                  -Dpassword=\${PASSWORD} \\
                  -Dbrowser=\${BROWSER} \\
                  -DbrowserVersion=\${BROWSER_VERSION} \\
                  -DbrowserSize=\${BROWSER_SIZE} \\
                  -DremoteUrl=https://\${SELENOID_USR}:\${SELENOID_PSW}@\${REMOTE_URL}/wd/hub
                """
            }
        }
    }
}