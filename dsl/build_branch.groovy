import hudson.model.*
import hudson.AbortException
import hudson.console.HyperlinkNote
import java.util.concurrent.CancellationException

def build_root = "/pipeline-${gitlabSourceRepoName}"


def test_script_path = build.buildVariableResolver.resolve("SCRIPT_PATH")
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
                    scriptPath("${SCRIPT_PATH}")
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
