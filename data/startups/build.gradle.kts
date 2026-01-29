plugins {
    id("appricot.android.library")
    id("appricot.android.hilt")
    id("appricot.apollo")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.appricot.startuphub.graphql.startups"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

apollo {
    service("api") {
        packageName.set("ru.appricot.startuphub.graphql.startups")
        dependsOn(project(":data:schema"))
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":data:schema"))
}
