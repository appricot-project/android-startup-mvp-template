package ru.appricot.startuphub.startups.model

import ru.appricot.startuphub.graphql.startups.GetStartupQuery
import ru.appricot.startuphub.graphql.startups.GetStartupsQuery

data class StartupModel(val id: String, val name: String?, val category: String?, val city: String?) {
    companion object {
        fun test(): List<StartupModel> = listOf(
            // AI startups
            StartupModel("1", "NeuroGen", "AI", "Moscow"),
            StartupModel("2", "NeuroPhoto", "AI", "Moscow"),
            StartupModel("3", "GPT-999", "AI", "Moscow"),
            StartupModel("4", "AI-GPT", "AI", "Moscow"),

            // Fintech startups
            StartupModel("5", "PayWave", "Fintech", "Washington"),
            StartupModel("6", "PayWithoutCard", "Fintech", "London"),

            // Health startups
            StartupModel("7", "HeartWell", "Health", "London"),
            StartupModel("8", "NewLife", "Health", "Washington"),
            StartupModel("9", "Free Life", "Health", "Moscow"),
        )

        fun from(dto: GetStartupsQuery.Node): StartupModel = StartupModel(
            id = dto.documentId,
            name = dto.title,
            city = dto.location,
            category = dto.category?.title,
        )

        fun from(dto: GetStartupQuery.Startup?): StartupModel? = if (dto == null) {
            null
        } else {
            StartupModel(
                id = dto.documentId,
                name = dto.title,
                city = dto.location,
                category = dto.category?.title,
            )
        }
    }
}
