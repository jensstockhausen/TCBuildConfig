package Main

import Main.buildTypes.BuildCheckSystem.BuildCheckSystem
import Main.buildTypes.BuildCompileProject.BuildCompileProject
import Main.buildTypes.BuildRunTests.BuildRunTests
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.sequential


class MainProject() : Project({

    subProject(ProjectChain(branch = "main"))

})

class ProjectChain(branch : String) : Project({
    name = "MainProject on $branch"
    id("MainProject_On_$branch")

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


