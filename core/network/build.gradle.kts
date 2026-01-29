plugins {
    id("appricot.android.library")
    id("appricot.android.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.appricot.startuphub.core.network"
}

dependencies {
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.normalized.cache)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    releaseImplementation(libs.chucker.release)
    debugImplementation(libs.chucker.debug)
}
