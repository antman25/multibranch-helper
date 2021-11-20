print("Making job - ${JOB_ROOT}/branch-cleanup")
pipelineJob("${JOB_ROOT}/branch-cleanup") {

    description("Cleanup job for ${JOB_ROOT}")

    environmentVariables {
        env('JOB_ROOT', "${JOB_ROOT}")
        keepBuildVariables(true)
    }

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
