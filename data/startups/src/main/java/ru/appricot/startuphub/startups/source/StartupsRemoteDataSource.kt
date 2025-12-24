package ru.appricot.startuphub.startups.source

import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.appricot.startuphub.graphql.startups.GetStartupsQuery
import javax.inject.Inject

class StartupsRemoteDataSource @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun getStartups(): List<GetStartupsQuery.Startup?> = withContext(Dispatchers.IO) {
        apolloClient.query(GetStartupsQuery()).execute().data?.startups.orEmpty()
    }
}
