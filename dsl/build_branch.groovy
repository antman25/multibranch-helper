def build_root = "/pipeline-${gitlabSourceRepoName}"

println("Test path: ${SCRIPT_PATH}")

folder("${build_root}")

println("In Groovy dsl: ${ACTIVE_BRANCHES}")

folder("${build_root}/${gitlabSourceBranch}")
pipelineJob("${build_root}/${gitlabSourceBranch}/main-pipeline") {
    displayName("000 - Main Pipeline")
    description("Pipeline for ${gitlabSourceRepoName}")

    environmentVariables {
        env('SOURCE_BRANCH', "${gitlabSourceBranch}")
        keepBuildVariables(true)
    }

    definition {
        cpsScm {
            scm {
                git {
                    remote { url("${gitlabSourceRepoHttpUrl}") }
                    branches("${gitlabSourceBranch}")
                    scriptPath("${SCRIPT_PATH}")
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
