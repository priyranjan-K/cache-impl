pipeline {
    agent any

    // ─────────────────────────────────────────────────────────────────────────
    // Environment variables
    // ─────────────────────────────────────────────────────────────────────────
    environment {
        // CVSS threshold: 7 = HIGH, 9 = CRITICAL
        // Change to 10 to disable build failure (report-only mode)
        OWASP_FAIL_CVSS = '7'

        // Set to 'true' to allow develop branch to continue even with findings
        // Set to 'false' to fail on both develop and main
        DEVELOP_WARN_ONLY = 'false'
    }

    stages {

        // ─────────────────────────────────────────────────────────────────────
        stage('Checkout') {
        // ─────────────────────────────────────────────────────────────────────
            steps {
                git branch: env.BRANCH_NAME ?: 'develop',
                    url: 'https://github.com/priyranjan-K/cache-impl.git'
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        stage('Build') {
        // ─────────────────────────────────────────────────────────────────────
            steps {
                // Fixed: 'mvn clean build' is not valid; correct goal is 'package'
                sh 'mvn clean package -DskipTests --no-transfer-progress'
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        stage('Unit Tests') {
        // ─────────────────────────────────────────────────────────────────────
            steps {
                sh 'mvn test --no-transfer-progress'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        stage('Security Scan - Dependencies (OWASP)') {
        // ─────────────────────────────────────────────────────────────────────
        // Scans all Maven dependencies against the NVD CVE database.
        // FAILS the pipeline if any dependency has CVSS >= 7 (HIGH/CRITICAL).
        // ─────────────────────────────────────────────────────────────────────
            steps {
                script {
                    try {
                        sh """
                            mvn org.owasp:dependency-check-maven:check \
                                -DfailBuildOnCVSS=${OWASP_FAIL_CVSS} \
                                -Dformat=ALL \
                                -DsuppressionFiles=owasp-suppressions.xml \
                                --no-transfer-progress
                        """
                    } catch (Exception e) {
                        if (env.BRANCH_NAME == 'develop' && env.DEVELOP_WARN_ONLY == 'true') {
                            echo "⚠️  WARNING: HIGH/CRITICAL vulnerabilities found on develop branch!"
                            echo "⚠️  Pipeline continues (DEVELOP_WARN_ONLY=true) but fix before merging to main."
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            echo "❌ CRITICAL: HIGH/CRITICAL CVE vulnerabilities found — BLOCKING MERGE"
                            throw e
                        }
                    }
                }
            }
            post {
                always {
                    // Publish OWASP HTML report in Jenkins
                    publishHTML(target: [
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target',
                        reportFiles: 'dependency-check-report.html',
                        reportName: 'OWASP Dependency-Check Report'
                    ])
                    // Archive the reports
                    archiveArtifacts artifacts: 'target/dependency-check-report.*',
                                     allowEmptyArchive: true
                }
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        stage('Security Scan - Source Code (SpotBugs SAST)') {
        // ─────────────────────────────────────────────────────────────────────
        // Static Application Security Testing on Java source code.
        // Detects: SQL injection, XSS, path traversal, insecure crypto, etc.
        // ─────────────────────────────────────────────────────────────────────
            steps {
                script {
                    try {
                        sh """
                            mvn spotbugs:check \
                                -Dspotbugs.effort=Max \
                                -Dspotbugs.threshold=Low \
                                -Dspotbugs.failOnError=true \
                                --no-transfer-progress
                        """
                    } catch (Exception e) {
                        if (env.BRANCH_NAME == 'develop' && env.DEVELOP_WARN_ONLY == 'true') {
                            echo "⚠️  WARNING: Security bugs found in source code on develop branch!"
                            echo "⚠️  Pipeline continues (DEVELOP_WARN_ONLY=true) but fix before merging to main."
                            currentBuild.result = 'UNSTABLE'
                        } else {
                            echo "❌ CRITICAL: Security vulnerabilities found in source code — BLOCKING MERGE"
                            throw e
                        }
                    }
                }
            }
            post {
                always {
                    // Archive SpotBugs XML report
                    archiveArtifacts artifacts: 'target/spotbugsXml.xml',
                                     allowEmptyArchive: true
                }
            }
        }

        // ─────────────────────────────────────────────────────────────────────
        stage('Package') {
        // ─────────────────────────────────────────────────────────────────────
            steps {
                sh 'mvn package -DskipTests --no-transfer-progress'
            }
        }

        //stage('Deploy') {
        //    steps {
        //        // Add your deployment steps here, e.g., using SCP, SSH, Docker, etc.
        //        sh 'scp build/libs/*.jar user@server:/path/to/deploy'
        //    }
        //}
    }

    post {
        success {
            echo '✅ All security scans PASSED. Build and pipeline succeeded!'
        }
        unstable {
            echo '⚠️  Pipeline completed with WARNINGS — security findings exist on develop branch. Fix before merging to main!'
        }
        failure {
            echo '❌ Pipeline FAILED — HIGH/CRITICAL security vulnerabilities found. Merge to main is BLOCKED.'
        }
        always {
            // Clean up workspace to avoid stale build artifacts
            cleanWs()
        }
    }
}