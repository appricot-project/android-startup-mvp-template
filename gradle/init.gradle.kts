val ktlintVersion = "1.8.0"

initscript {
    val spotlessVersion = "8.1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:$spotlessVersion")
        classpath("io.nlopez.compose.rules:ktlint:0.5.2")
    }
}

rootProject {
    subprojects {
        apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
        extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
            kotlin {
                target("**/*.kt")
                targetExclude("**/build/**/*.kt")
                ktlint(ktlintVersion)
                    .editorConfigOverride(
                        mapOf(
                            "ktlint_standard_condition-wrapping" to "disabled",
                        ),
                    )
                    .customRuleSets(
                        listOf(
                            "io.nlopez.compose.rules:ktlint:0.5.2"
                        )
                    )
            }
            format("kts") {
                target("**/*.kts")
                targetExclude("**/build/**/*.kts")
            }
            format("xml") {
                target("**/*.xml")
                targetExclude("**/build/**/*.xml")
            }
        }
    }
}
