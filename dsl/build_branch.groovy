def build_root = '/build-root-proto'
folder("${build_root}")
//print("Params Build Root: ${env}")
print("Building Folder ${build_root}/${gitlabSourceBranch}")
folder("${build_root}/${gitlabSourceBranch}")


print("Making job - ${build_root}/${gitlabSourceBranch}/build-master")
pipelineJob("${build_root}/${gitlabSourceBranch}/build-master") {

    def repo = 'http://gitlab.antlinux.local:30080/antman/pipeline-proto.git'

    description("Pipeline for $repo")

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
                    remote { url(repo) }
                    branches("${gitlabSourceBranch}")
                    scriptPath('build-master/Jenkinsfile')
                    extensions { }  // required as otherwise it may try to tag the repo, which you may not want
                }
            }
        }
    }
}
