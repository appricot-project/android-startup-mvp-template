package ru.appricot.startuphub.startups.source

import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.appricot.startuphub.graphql.startups.GetStartupsQuery
import ru.appricot.startuphub.startups.model.StartupModel
import javax.inject.Inject

class RemoteStartupsDataSource @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun getStartups(): List<StartupModel> = withContext(Dispatchers.IO) {
        apolloClient.query(GetStartupsQuery()).execute()
            .dataAssertNoErrors
            .startups
            .filterNotNull()
            .map {
                StartupModel.from(it)
            }
    }
}
