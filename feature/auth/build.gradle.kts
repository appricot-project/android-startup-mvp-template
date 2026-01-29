plugins {
    id("appricot.android.feature")
    id("appricot.android.library.compose")
    id("appricot.android.hilt")
}

android {
    namespace = "ru.apprictor.startuphub.auth"
}

dependencies {
    implementation(project(":feature:auth_api"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation3.runtime)
}