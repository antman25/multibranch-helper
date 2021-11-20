print("Making job - ${JOB_ROOT}/branch-cleanup")
print("REPO_URL = ${REPO_URL}")
pipelineJob("${JOB_ROOT}/branch-cleanup") {

    description("Cleanup job for ${JOB_ROOT}")

    parameters {
        stringParam('JOB_ROOT', "${JOB_ROOT}", 'JOB_ROOT')
        stringParam('REPO_URL', "${REPO_URL}", 'REPO_URL')
    }

    /*environmentVariables {
        env('JOB_ROOT', "${JOB_ROOT}")
        env('REPO_URL', "${REPO_URL}")
        keepBuildVariables(true)
    }*/

    definition {
        cpsScm {
            scm {
                git {
                    remote { url('http://gitlab.antlinux.local:30080/antman/pipeline-proto-helper.git') }
                    branches('main')
                    scriptPath('cleanup/Jenkinsfile')
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
