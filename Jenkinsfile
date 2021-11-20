@Library('jenkins-shared-lib') _

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


    stage('Git Clone')
    {
        /*checkout([$class: 'GitSCM',
                branches: [[name: source_branch]],
                extensions: [],
                userRemoteConfigs:
                [[credentialsId: 'jenkins_ssh', url: repo]]])*/
                checkout scm
    }

    stage ('Create Branch')
    {
         //def extra_params = params
         //extra_params['JOB_ROOT'] = build_root

         jobDsl targets: ["dsl/build_branch.groovy"].join('\n'),
         removedJobAction: 'IGNORE',
         removedViewAction: 'IGNORE',
         lookupStrategy: 'SEED_JOB',
         additionalParameters: params
         /*jobDsl scriptText: "folder('${build_root}')"
         jobDsl scriptText: "folder('${build_root}/${gitlabSourceBranch}')"*/

    }

    stage('Run Pipeline')
    {
        def param_array = []
        /*env.each { k,v ->
            print("Key: ${k} Val: ${v}")
            param_array.add([$class: 'StringParameterValue', name: k, value: v])
        }*/
        print("Sending Source Branch: ${source_branch}")
        build job: "${build_root}/${gitlabSourceBranch}/build-master"
              parameters: [string(name: 'SOURCE_BRANCH', value: source_branch)]
    }

}
