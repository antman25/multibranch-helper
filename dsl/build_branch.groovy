folder("${JOB_ROOT}")
//print("Params Build Root: ${env}")
print("Building Folder ${JOB_ROOT}/${gitlabSourceBranch}")
folder("${JOB_ROOT}/${gitlabSourceBranch}")


print("Making job - ${JOB_ROOT}/${gitlabSourceBranch}/main-pipeline")
pipelineJob("${JOB_ROOT}/${gitlabSourceBranch}/main-pipeline") {

    description("Pipeline for ${REPO_URL}")

    /*parameters {
        stringParam('SOURCE_BRANCH', '', 'build this branch')
    }*/

    environmentVariables {
        env('SOURCE_BRANCH', "${gitlabSourceBranch}")
        keepBuildVariables(true)
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote { url("${REPO_URL}") }
                    branches("${gitlabSourceBranch}")
                    scriptPath("${SCRIPT_PATH}")
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
