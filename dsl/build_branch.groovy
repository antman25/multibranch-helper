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

        /*
        gitlabActionType=PUSH
        gitlabAfter=c386efc2218594f95a8ac2c794321bb7397c1ca2
        gitlabBefore=df7eec621763b9a8219c45e307dba91121675ac1
        gitlabBranch=main
        gitlabMergeRequestLastCommit=c386efc2218594f95a8ac2c794321bb7397c1ca2
        GITLAB_OBJECT_KIND=none
        gitlabSourceBranch=main
        gitlabSourceNamespace=antman
        gitlabSourceRepoHomepage=http://gitlab.antlinux.local:30080/antman/pipeline-proto-helper
        gitlabSourceRepoHttpUrl=http://gitlab.antlinux.local:30080/antman/pipeline-proto-helper.git
        gitlabSourceRepoName=pipeline-proto-helper
        gitlabSourceRepoSshUrl=git@gitlab.antlinux.local:antman/pipeline-proto-helper.git
        gitlabSourceRepoURL=git@gitlab.antlinux.local:antman/pipeline-proto-helper.git
        gitlabTargetBranch=main
        gitlabUserName=antman
        gitlabUserUsername=antman
         */

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
