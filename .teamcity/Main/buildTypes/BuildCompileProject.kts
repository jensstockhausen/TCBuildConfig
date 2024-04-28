package Main.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

class BuildCompileProject(projectName: String) : BuildType({

    id = RelativeId("BuildCompileProject_$projectName")
    name = "BuildCompileProject for $projectName"

    vcs {
    }

    steps {
        script {
            name = "compile"
            id = "compile"
            scriptContent = """
                echo ${'$'}PWD > info.txt
                echo $projectName >> info.txt
            """.trimIndent()
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

