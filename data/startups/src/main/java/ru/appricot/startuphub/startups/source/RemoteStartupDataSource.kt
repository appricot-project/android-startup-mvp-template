package ru.appricot.startuphub.startups.source

import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.appricot.startuphub.graphql.startups.GetStartupQuery
import ru.appricot.startuphub.startups.model.StartupDetailsModel
import javax.inject.Inject

class RemoteStartupDataSource @Inject constructor(private val apolloClient: ApolloClient) {
    suspend fun getStartup(id: String?): StartupDetailsModel? = withContext(Dispatchers.IO) {
        if (id.isNullOrBlank()) {
            null
        } else {
            StartupDetailsModel.from(
                apolloClient.query(GetStartupQuery(id)).execute()
                    .dataAssertNoErrors
                    .startup,
            )
        }
    }
}
