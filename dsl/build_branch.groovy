def build_root = "/pipeline-${gitlabSourceRepoName}"

println("Test path: ${SCRIPT_PATH}")

folder("${build_root}")

println("In Groovy dsl: ${ACTIVE_BRANCHES}")

def active_branches = "${ACTIVE_BRANCHES}"
def active_branches_split = active_branches.split(",")

active_branches_split.each { cur_branch ->
    println("Cur Active Branch (Groovy): ${cur_branch}")

    folder("${build_root}/${cur_branch}")
    pipelineJob("${build_root}/${cur_branch}/main-pipeline") {
        displayName("000 - Main Pipeline")
        description("Pipeline for ${gitlabSourceRepoName}")

        environmentVariables {
            env('SOURCE_BRANCH', "${cur_branch}")
            keepBuildVariables(true)
        }

        definition {
            cpsScm {
                scm {
                    git {
                        remote { url("${gitlabSourceRepoHttpUrl}") }
                        branches("${cur_branch}")
                        scriptPath("${SCRIPT_PATH}")
                        extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                    }
                }
            }
        }
    }
}



/*folder("${build_root}/${gitlabSourceBranch}")
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
}*/
