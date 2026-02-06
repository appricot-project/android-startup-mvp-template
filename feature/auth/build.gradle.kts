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
    implementation(project(":data:auth"))
    api(libs.openid)
}
