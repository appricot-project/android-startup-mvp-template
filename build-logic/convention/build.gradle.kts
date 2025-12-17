import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ru.appricot.startuphub.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "appricot.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "appricot.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "appricot.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidFeatureApi") {
            id = "appricot.android.feature.api"
            implementationClass = "AndroidFeatureApiConventionPlugin"
        }
        register("androidApplication") {
            id = "appricot.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidHilt") {
            id = "appricot.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidFirebase") {
            id = "appricot.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
    }
}
