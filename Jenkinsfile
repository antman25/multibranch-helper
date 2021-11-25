@Library('jenkins-shared-lib') _
node()
{
    //print("gitlabSourceBranch = ${env.gitlabSourceBranch}")
    //print("ENV = ${env.getEnvironment()}")
    //def source_branch = env.getEnvironment().getOrDefault("BRANCH_NAME", "main")

    def repo = env.getEnvironment().getOrDefault("gitlabSourceRepoHttpUrl", "NOTSET")
    def source_repo = env.getEnvironment().getOrDefault("gitlabSourceRepoName", "NOTSET")
    def source_branch = env.getEnvironment().getOrDefault("gitlabSourceBranch", "NOTSET")
    def build_root = "pipeline-${source_repo}"
    def script_path = params.getOrDefault("SCRIPT_PATH", "Jenkinsfile")
    print("Using SCRIPT_PATH = ${script_path}")

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
                 def extra_params = [:]
                 params.each { k,v ->
                    extra_params[k] = v
                 }
                 def active_branches = git_helper.getRemoteBranches(repo)
                 extra_params['ACTIVE_BRANCHES'] = active_branches.join(',')
                 //print("ExtraParams: ${extra_params}")
                     jobDsl targets: ["dsl/build_branch.groovy"].join('\n'),
                     removedJobAction: 'DELETE',
                     removedViewAction: 'DELETE',
                     lookupStrategy: 'SEED_JOB',
                     additionalParameters: extra_params

            }
        }

        stage('Run Pipeline')
        {
            gitlabCommitStatus(name: "build")
            {
                print("Sending Source Branch: ${source_branch}")
                def extra_params = []
                params.each { k,v ->
                    if (k.startWith('gitlab'))
                    {
                        extra_params.append(string(name: k, value: v))
                    }
                 }

                build job: "${build_root}/${gitlabSourceBranch}/main-pipeline"
                      parameters: extra_params
            }
        }
    }
}
