package Main

import Main.buildTypes.BuildCheckSystem.BuildCheckSystem
import Main.buildTypes.BuildCompileProject.BuildCompileProject
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.sequential


object MainProject : Project({

    val bts = sequential {

        buildType(BuildCheckSystem())

        parallel {
            buildType(BuildCompileProject(projectName = "PrjA"))
            buildType(BuildCompileProject(projectName = "PrjB"))
        }

    }.buildTypes()

    bts.forEach { bt -> buildType(bt)}

})


