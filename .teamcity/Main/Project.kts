package Main

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.DslContext
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs


object MainProject : Project({
    id("Main.ProjectId")
    name = "Main.Project"
    parentId("ROOT")

    buildType(Build)
})

object Build : BuildType({
    name = "Build"

    vcs {
        //root(DslContext.settingsRoot)
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

