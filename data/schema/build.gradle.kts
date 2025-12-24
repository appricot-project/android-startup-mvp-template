@file:OptIn(ApolloExperimental::class)

import com.apollographql.apollo.annotations.ApolloExperimental

plugins {
    id("appricot.android.library")
    id("appricot.android.hilt")
    id("appricot.apollo")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.appricot.startuphub.graphql"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

apollo {
    service("api") {
        packageName.set("ru.appricot.startuphub.graphql.schema")

        generateApolloMetadata.set(true)
        schemaFiles.from("src/main/graphql")
        generateDataBuilders.set(true)
        useSemanticNaming.set(true)
        generateFragmentImplementations.set(true)
    }
}

dependencies {
}
