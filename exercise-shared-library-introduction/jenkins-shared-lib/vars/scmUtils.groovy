def checkout(params) {
    checkout([
        $class: 'GitSCM',
        branches: [[name:  params.branch ]],
        userRemoteConfigs: [[ url: params.url, credentialsId: params.credentialsId ]],
        extensions: [[$class: 'LocalBranch']]
    ])
}

def commit(params) {
    if(!params.message) {
        error("Missing params.message")
    }
    sh "git add ${params.path ?: '.'}"
    sh "git commit -m ${params.message}"
}

def push(params) {
    withCredentials(
        [usernamePassword(credentialsId: params.credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]
    ) {
        sh "git push https://\${PASSWORD}@github.com/${params.gitHubPath}.git"
    }
}

def setGitUserName(username) {
    sh "git config user.name ${username}"
}

def setGitUserMail(email) {
   sh "git config user.email ${email}"
}

