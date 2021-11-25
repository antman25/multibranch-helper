def build_root = "/pipeline-${gitlabSourceRepoName}"

println("script path: ${script_path}")

folder("${build_root}")

println("In Groovy dsl: ${active_branches}")

//def active_branches = "${ACTIVE_BRANCHES}"
def active_branches_split = active_branches.split(",")

active_branches_split.each { cur_branch ->
    println("Cur Active Branch (Groovy): ${cur_branch}")

    folder("${build_root}/${cur_branch}")
    pipelineJob("${build_root}/${cur_branch}/main-pipeline") {
        displayName("000 - Main Pipeline")
        description("Pipeline for ${gitlabSourceRepoName}")

        parameters {
            stringParam('gitlabActionType', String.valueOf(gitlabActionType))
            stringParam('gitlabAfter', String.valueOf(gitlabAfter))
            stringParam('gitlabBefore', String.valueOf(gitlabBefore))
            stringParam('gitlabBranch', String.valueOf(gitlabBranch))
            stringParam('gitlabMergeRequestLastCommit', String.valueOf(gitlabMergeRequestLastCommit))
            stringParam('gitlabSourceBranch', String.valueOf(gitlabSourceBranch))
            stringParam('gitlabSourceNamespace', String.valueOf(gitlabSourceNamespace))
            stringParam('gitlabSourceRepoHomepage', String.valueOf(gitlabSourceRepoHomepage))
            stringParam('gitlabSourceRepoHttpUrl', String.valueOf(gitlabSourceRepoHttpUrl))
            stringParam('gitlabSourceRepoName', String.valueOf(gitlabSourceRepoName))
            stringParam('gitlabSourceRepoSshUrl', String.valueOf(gitlabSourceRepoSshUrl))
            stringParam('gitlabSourceRepoURL', String.valueOf(gitlabSourceRepoURL))
            stringParam('gitlabTargetBranch', String.valueOf(gitlabTargetBranch))
            stringParam('gitlabUserName', String.valueOf(gitlabUserName))
            stringParam('gitlabUserUsername', String.valueOf(gitlabUserUsername))
        }

        properties {
            gitLabConnectionProperty {
                gitLabConnection "gitlab1"
            }
        }

        /*environmentVariables {
            env('SOURCE_BRANCH', "${cur_branch}")
            keepBuildVariables(true)
        }*/

        definition {
            cpsScm {
                scm {
                    git {
                        remote { url(gitlabSourceRepoHttpUrl) }
                        branches(cur_branch)
                        scriptPath(script_path)
                        extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                    }
                }
            }
        }
    }
}
