package Main

import Main.buildTypes.BuildCheckSystem.BuildCheckSystem
import Main.buildTypes.BuildCompileProject.BuildCompileProject
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.RelativeId


object MainProject : Project({
    id = RelativeId("MainProject")
    name = "Main.Project"
    parentId("ROOT")

    buildType(BuildCheckSystem)
    buildType(BuildCompileProject)

})


