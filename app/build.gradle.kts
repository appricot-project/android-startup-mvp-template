import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("appricot.android.application")
    id("appricot.android.hilt")
    id("appricot.android.application.firebase")
}

android {
    val localProps = gradleLocalProperties(rootDir, providers)
    val signingKeyAlias =
        localProps.getProperty("signing.keyAlias") ?: error("Missing signing.keyAlias in local.properties")
    val signingStorePassword =
        localProps.getProperty("signing.storePassword") ?: error("Missing signing.storePassword in local.properties")
    val signingKeyPassword =
        localProps.getProperty("signing.keyPassword") ?: error("Missing signing.keyPassword in local.properties")
    namespace = "ru.appricot.startuphub"
    compileSdk = 36
    buildToolsVersion = "36.0.0"
    defaultConfig {
        applicationId = "ru.appricot.startuphub"
        versionCode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH")).toInt()
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        minSdk = 26

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    base.archivesName.set("StartupHub-${defaultConfig.versionName}(${defaultConfig.versionCode})")
    signingConfigs {
        create("release") {
            keyAlias = signingKeyAlias
            storePassword = signingStorePassword
            keyPassword = signingKeyPassword
            storeFile = file("../keys/hubKey.jks")
        }
    }
    buildTypes {
        val debug by getting {
            applicationIdSuffix = ru.appricot.startuphub.BuildType.DEBUG.applicationIdSuffix
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
        val release by getting {
            isMinifyEnabled = false
            applicationIdSuffix = ru.appricot.startuphub.BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))

    implementation(project(":feature:home_api"))
    implementation(project(":feature:home"))

    implementation(project(":feature:profile_api"))
    implementation(project(":feature:profile"))

    implementation(libs.androidx.junit)
    debugImplementation(libs.leakcanary.android)
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0")
}
