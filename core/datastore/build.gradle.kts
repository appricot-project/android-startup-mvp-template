plugins {
    id("appricot.android.library")
    id("appricot.android.hilt")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ru.appricot.startuphub.core.datastore"
}

dependencies {
    api(libs.datastore)
}
