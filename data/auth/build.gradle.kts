plugins {
    id("appricot.android.library")
    id("appricot.android.hilt")
}

android {
    namespace = "ru.appricot.startuphub.auth"
}

dependencies {
    implementation(project(":core:network"))
}
