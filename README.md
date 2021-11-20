# pipeline-proto-helper

Needed job parameters:
---Param Name: REPO_URL---
Type: String
Description: This is the url to look for remote branches
Example: http://gitlab.antlinux.local:30080/antman/pipeline-proto.git

---Param Name: JOB_ROOT---
Type: String
Description: This is the root path of the jenkins jobs to monitor
Example: /root-pipline-path

---Param Name: SCRIPT_PATH---
Type: String
Description: Path to the pipeline job to execute 
Example: Jenkinsfile