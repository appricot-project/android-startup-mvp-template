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
                    .customRuleSets(
                        listOf(
                            "io.nlopez.compose.rules:ktlint:0.5.2"
                        )
                    )
                    .editorConfigOverride(
                        mapOf(
                            "ktlint_standard_condition-wrapping" to "disabled",
                            "ktlint_standard_function-naming" to "disabled",
                            "ktlint_compose_compositionlocal-allowlist" to "disabled"
                        ),
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
