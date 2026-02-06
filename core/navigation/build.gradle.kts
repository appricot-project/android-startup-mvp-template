plugins {
    id("appricot.android.library")
    id("appricot.android.library.compose")
    id("appricot.android.hilt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ru.appricot.startuphub.core.navigation"
}

dependencies {
    api(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.compose.runtime)
}
