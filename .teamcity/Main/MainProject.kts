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

    val buildCheckSystem = BuildCheckSystem(branch = branch)

    val buildCompileProjectA = BuildCompileProject(projectName = "PrjA", branch = branch)
    val buildCompileProjectB = BuildCompileProject(projectName = "PrjB", branch = branch)

    val buildRunTestsA = BuildRunTests(projectName = "PrjA", branch = branch)
    val buildRunTestsB = BuildRunTests(projectName = "PrjB", branch = branch)
    val buildRunTestsAB = BuildRunTests(projectName = "Integ_PrjA_PrjB", branch = branch)

    buildType(buildCheckSystem)

    subProject {
        name = "Sub Compile on $branch"
        id("Sub_Compile_On_$branch")
        buildType(buildCompileProjectA)
        buildType(buildCompileProjectB)
    }

    subProject {
        name = "Sub Test on $branch"
        id("Sub_Test_On_$branch")
        buildType(buildRunTestsA)
        buildType(buildRunTestsB)
        buildType(buildRunTestsAB)
    }


    buildRunTestsA.dependencies {
        artifacts(buildCompileProjectA){
            artifactRules = "*.txt => ./"
            cleanDestination = true
        }
    }

    buildRunTestsB.dependencies {
        artifacts(buildCompileProjectB){
            artifactRules = "*.txt => ./"
            cleanDestination = true
        }
    }


    val bts = sequential {
        buildType(buildCheckSystem)
        parallel {
            sequential {
                buildType(buildCompileProjectA)
                buildType(buildRunTestsA)
            }
            sequential {
                buildType(buildCompileProjectB)
                buildType(buildRunTestsB)
            }
        }
        buildType(buildRunTestsAB)
    }.buildTypes()

    //bts.forEach { bt -> buildType(bt)}

    buildTypesOrder = arrayListOf(
        buildCheckSystem,
        buildCompileProjectA, buildCompileProjectA,
        buildCompileProjectB, buildCompileProjectB,
        buildRunTestsAB)

})


