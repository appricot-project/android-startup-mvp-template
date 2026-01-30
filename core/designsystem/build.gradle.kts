plugins {
    id("appricot.android.library")
    id("appricot.android.library.compose")
}

android {
    namespace = "ru.appricot.startuphub.designsystem"
}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.material3.adaptive)
    api(libs.androidx.compose.material3.navigationSuite)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.compose.ui.text)
}
