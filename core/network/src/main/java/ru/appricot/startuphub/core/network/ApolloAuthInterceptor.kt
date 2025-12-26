package ru.appricot.startuphub.core.network

import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApolloAuthInterceptor @Inject constructor() : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(request: ApolloRequest<D>, chain: ApolloInterceptorChain): Flow<ApolloResponse<D>> =
        chain.proceed(
            request.newBuilder()
                .addHttpHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.GRAPHQL_AUTH_KEY}",
                )
                .build(),
        )
}
