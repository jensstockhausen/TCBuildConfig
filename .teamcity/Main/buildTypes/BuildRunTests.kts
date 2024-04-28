package Main.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.RelativeId
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

class BuildRunTests(branch : String, projectName: String) : BuildType({

    id = RelativeId("BuildRunTests_$projectName" + "_$branch")
    name = "BuildRunTests for $projectName on$branch "

    vcs {
    }

    steps {
        script {
            name = "test"
            id = "test"
            scriptContent = """
                echo "##teamcity[testSuiteStarted name='suiteName$projectName']"
                
                echo "##teamcity[testStarted name='testName$projectName']"
                echo "##teamcity[testFinished name='testName$projectName']"
                
                echo "##teamcity[testSuiteFinished name='suiteName$projectName']"
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

