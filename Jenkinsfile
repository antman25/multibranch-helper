node()
{
    //print("gitlabSourceBranch = ${env.gitlabSourceBranch}")
    //print("ENV = ${env.getEnvironment()}")
    //def source_branch = env.getEnvironment().getOrDefault("BRANCH_NAME", "main")
    def build_root = params.getOrDefault("JOB_ROOT", "/UnknownJobRoot")
    def repo = params.getOrDefault("REPO_URL", "NOTSET")
    def source_branch = env.getEnvironment().getOrDefault("gitlabSourceBranch", "main")
    stage ("ENV Dump")
    {
        sh ("env | sort -n")
        print("Params: ${params}")
        print("Source Branch: ${source_branch}")
    }
    gitlabBuilds(builds: ["git", "dsl","build"]) {

        stage('Git Clone')
        {
            gitlabCommitStatus(name: "git")
            {
                checkout scm
            }
        }

        stage ('JobDSL')
        {
            gitlabCommitStatus(name: "dsl")
            {
                 //def extra_params = params
                 //extra_params['JOB_ROOT'] = build_root

                 jobDsl targets: ["dsl/build_branch.groovy",
                                  "dsl/build_cleanup.groovy"].join('\n'),
                 removedJobAction: 'IGNORE',
                 removedViewAction: 'IGNORE',
                 lookupStrategy: 'SEED_JOB',
                 additionalParameters: params
            }
        }

        stage('Run Pipeline')
        {
            gitlabCommitStatus(name: "build")
            {
                print("Sending Source Branch: ${source_branch}")
                build job: "${build_root}/${gitlabSourceBranch}/main-pipeline"
                      parameters: [string(name: 'SOURCE_BRANCH', value: source_branch)]
            }
        }
    }
}
