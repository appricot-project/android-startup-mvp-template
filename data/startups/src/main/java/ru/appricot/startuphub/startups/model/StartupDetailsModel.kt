package ru.appricot.startuphub.startups.model

import ru.appricot.startuphub.graphql.startups.GetStartupQuery

data class StartupDetailsModel(
    val id: String,
    val name: String?,
    val category: String?,
    val city: String?,
    val description: String?,
    val imageUrl: String?,
) {

    companion object {
        fun from(dto: GetStartupQuery.Startup?): StartupDetailsModel = StartupDetailsModel(
            id = dto?.documentId.orEmpty(),
            name = dto?.title,
            category = dto?.category?.title,
            city = dto?.location,
            description = dto?.description,
            imageUrl = dto?.ImageURL?.url,
        )
    }
}
