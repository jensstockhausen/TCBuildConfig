package Main.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

class BuildCompileProject() : BuildType({
    id = RelativeId("BuildCompileProject")
    name = "Main.buildTypes.BuildCompileProject"

    vcs {
    }

    steps {
        script {
            name = "compile"
            id = "compile"
            scriptContent = "echo ${'$'}PWD > info.txt"
        }
    }

    artifactRules = "info.txt"

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

