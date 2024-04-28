package Main.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

class BuildCheckSystem(branch : String) : BuildType({
    id = RelativeId("BuildCheckSystem_$branch")
    name = "Main.buildTypes.BuildCheckSystem on $branch"

    vcs {
    }

    steps {
        script {
            name = "get pwd"
            id = "get_pwd"
            scriptContent = "echo ${'$'}PWD"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
