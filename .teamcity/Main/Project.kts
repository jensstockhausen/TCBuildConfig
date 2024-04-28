package Main

import Main.buildTypes.BuildCheckSystem.BuildCheckSystem
import Main.buildTypes.BuildCompileProject.BuildCompileProject
import Main.buildTypes.BuildRunTests.BuildRunTests
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.sequential


object MainProject : Project({

    val bts = sequential {

        buildType(BuildCheckSystem())

        parallel {

            sequential {
                buildType(BuildCompileProject(projectName = "PrjA"))
                buildType(BuildRunTests(projectName = "PrjA"))
            }

            sequential {
                buildType(BuildCompileProject(projectName = "PrjB"))
                buildType(BuildRunTests(projectName = "PrjB"))
            }

        }

        buildType(BuildRunTests(projectName = "Integ_PrjA_PrjB"))

    }.buildTypes()

    bts.forEach { bt -> buildType(bt)}

})


