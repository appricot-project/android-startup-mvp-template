plugins {
    id("appricot.android.feature")
    id("appricot.android.library.compose")
    id("appricot.android.hilt")
}
android {
    namespace = "ru.apprictor.startuphub.home"
}

dependencies {
    implementation(project(":feature:home_api"))
    implementation(project(":data:startups"))
}
