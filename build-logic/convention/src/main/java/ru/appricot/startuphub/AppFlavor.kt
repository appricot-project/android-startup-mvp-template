package ru.appricot.startuphub

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    environment
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class AppFlavor(
    val dimension: FlavorDimension,
    val buildConfigFields: List<BuildConfigField> = emptyList(),
    val applicationId: String? = null
) {
    stage(
        FlavorDimension.environment,
        listOf(
            BuildConfigField(
                "String",
                "BASE_URL",
                "\"cms.apps.appricot.ru/graphql\""
            ),
        ),
        "ru.appricot.startuphub.stage"
    ),
    prod(
        FlavorDimension.environment,
        listOf(
            BuildConfigField(
                "String",
                "BASE_URL",
                "\"https://cms.apps.appricot.ru/graphql\""
            ),
        ),
        "ru.appricot.startuphub"
    ),
}

class BuildConfigField(val type: String, val name: String, val value: String)

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {}
) {
    commonExtension.apply {
        FlavorDimension.values().forEach { dimension ->
            flavorDimensions += dimension.name
        }
        buildFeatures {
            buildConfig = true
        }
        productFlavors {
            AppFlavor.values().forEach { flavor ->
                register(flavor.name) {
                    dimension = flavor.dimension.name
                    flavorConfigurationBlock(this, flavor)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (flavor.applicationId != null) {
                            this.applicationId = flavor.applicationId
                            namespace = flavor.applicationId
                        }
                    }

                    flavor.buildConfigFields.forEach { field ->
                        if (field.type == "String" || field.type == "Boolean")
                            buildConfigField(field.type, field.name, field.value)
                        else resValue("string", field.name, field.value)
                    }

                    val localProps = gradleLocalProperties(rootDir, providers)
                    val authKey = localProps.getProperty("graphql.auth.token") ?: error("Missing graphql.auth.token in local.properties")
                    buildConfigField("String", "GRAPHQL_AUTH_KEY", "\"$authKey\"")
                }
            }
        }
    }
}

/*
Takes value from Gradle project property and sets it as Android build config property eg.
apiToken variable present in the settings.gradle file will be accessible as BuildConfig.GRADLE_API_TOKEN in the app.
 */
fun Project.buildConfigFieldFromGradleProperty(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    gradlePropertyName: String
) {
    commonExtension.apply {
        val propertyValue = project.properties[gradlePropertyName] as? String
        checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

        defaultConfig {
            val androidResourceName = "GRADLE_${gradlePropertyName}".uppercase()
            buildConfigField("String", androidResourceName, propertyValue)
        }
    }
}
