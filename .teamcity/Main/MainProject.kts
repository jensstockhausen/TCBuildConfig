package Main

import Main.buildTypes.BuildCheckSystem.BuildCheckSystem
import Main.buildTypes.BuildCompileProject.BuildCompileProject
import Main.buildTypes.BuildRunTests.BuildRunTests
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.sequential


class MainProject() : Project({

    subProject(ProjectChain(branch = "main"))
    subProject(ProjectChain(branch = "rel"))

})

class ProjectChain(branch : String) : Project({
    name = "MainProject on $branch"
    id("MainProject_On_$branch")

    val bts = sequential {

        buildType(BuildCheckSystem(branch = branch))

        parallel {

            sequential {
                buildType(BuildCompileProject(projectName = "PrjA", branch = branch))
                buildType(BuildRunTests(projectName = "PrjA", branch = branch))
            }

            sequential {
                buildType(BuildCompileProject(projectName = "PrjB", branch = branch))
                buildType(BuildRunTests(projectName = "PrjB", branch = branch))
            }

        }

        buildType(BuildRunTests(projectName = "Integ_PrjA_PrjB", branch = branch))

    }.buildTypes()

    bts.forEach { bt -> buildType(bt)}

})


