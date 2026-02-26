plugins {
    id("appricot.android.feature")
    id("appricot.android.library.compose")
    id("appricot.android.hilt")
}
android {
    namespace = "ru.apprictor.startuphub.profile"
}

dependencies {
    implementation(project(":feature:profile_api"))
    implementation(project(":feature:home_api"))
    implementation(project(":data:auth"))
}
