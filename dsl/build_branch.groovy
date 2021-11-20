def build_root = "/pipeline-${gitlabSourceRepoName}"

folder("${build_root}")
folder("${build_root}/${gitlabSourceBranch}")
pipelineJob("${build_root}/${gitlabSourceBranch}/main-pipeline") {

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
                    scriptPath("Jenkinsfile")
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
