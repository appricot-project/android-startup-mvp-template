plugins {
    id("appricot.android.library")
    id("appricot.android.library.compose")
}

android {
    namespace = "ru.appricot.startuphub.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
}
