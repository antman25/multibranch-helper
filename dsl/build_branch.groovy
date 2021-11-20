def build_root = "/pipeline-${gitlabSourceRepoName}"


def test_script_path = System.getenv("SCRIPT_PATH")

print("Test path: ${test_script_path}")

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
                    scriptPath("${test_script_path}")
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
