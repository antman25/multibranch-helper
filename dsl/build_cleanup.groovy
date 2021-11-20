def build_root = "/pipeline-${gitlabSourceRepoName}"


print("Making job - ${build_root}/branch-cleanup")
print("REPO_URL = ${gitlabSourceRepoHttpUrl}")
pipelineJob("${build_root}/branch-cleanup") {

    description("Cleanup job for ${build_root}")

    parameters {
        stringParam('JOB_ROOT', "${build_root}", 'JOB_ROOT')
        stringParam('REPO_URL', "${gitlabSourceRepoHttpUrl}", 'REPO_URL')
    }

    properties {
        pipelineTriggers {
            triggers {
                cron {
                    spec("H/2 * * * * ")
                }
            }
        }
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
