package ru.appricot.startuphub.core.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.network.okHttpClient
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GraphQLModule {

    @Provides
    @BaseUrl
    fun providesBaseUrl(): String = BuildConfig.BASE_URL

    @IntoSet
    @Provides
    fun providesApolloInterceptor(impl: ApolloAuthInterceptor): ApolloInterceptor = impl

    @Provides
    @Singleton
    fun providesCacheFactory(): NormalizedCacheFactory = MemoryCacheFactory()

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor = ChuckerInterceptor(context)

    @Provides
    @Singleton
    fun providesOkhttpClient(loggingInterceptor: HttpLoggingInterceptor, chuckerInterceptor: ChuckerInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideApolloClient(
        @BaseUrl endpoint: String,
        interceptors: Set<@JvmSuppressWildcards ApolloInterceptor>,
        cacheFactory: NormalizedCacheFactory,
        okHttpClient: OkHttpClient,
    ): ApolloClient = ApolloClient.Builder()
        .serverUrl(endpoint)
        .okHttpClient(okHttpClient)
        .interceptors(interceptors = interceptors.toList())
        .normalizedCache(cacheFactory)
        .fetchPolicy(FetchPolicy.NetworkFirst)
        .build()
}
