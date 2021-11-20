def build_root = "/pipeline-${gitlabSourceRepoName}"

folder("${build_root}")
//print("Params Build Root: ${env}")
print("Building Folder ${build_root}/${gitlabSourceBranch}")
folder("${build_root}/${gitlabSourceBranch}")


print("Making job - ${build_root}/${gitlabSourceBranch}/main-pipeline")
pipelineJob("${build_root}/${gitlabSourceBranch}/main-pipeline") {

    description("Pipeline for ${gitlabSourceRepoName}")

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
                    remote { url("${gitlabSourceRepoHttpUrl}") }
                    branches("${gitlabSourceBranch}")
                    scriptPath("Jenkinsfile")
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
