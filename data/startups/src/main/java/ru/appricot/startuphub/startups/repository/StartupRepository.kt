package ru.appricot.startuphub.startups.repository

import ru.appricot.startuphub.startups.model.StartupModel
import ru.appricot.startuphub.startups.source.StartupsRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartupRepository @Inject constructor(private val remoteDataStartupRepository: StartupsRemoteDataSource) {

    suspend fun getStartups(): List<StartupModel> = remoteDataStartupRepository.getStartups()
        .filterNotNull()
        .map {
            StartupModel.from(it)
        }

    /*    suspend fun getStartup(id: String): Result<GetStartupQuery.Startup?> {
            return try {
                val response = apolloClient.query(GetStartupQuery(id = id)).execute()
                if (response.hasErrors()) {
                    Result.failure(Exception(response.errors?.first()?.message))
                } else {
                    Result.success(response.data?.startup)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }*/

    /*suspend fun createStartup(input: CreateStartupMutation.StartupInput): Result<CreateStartupMutation.CreateStartup> {
        return try {
            val response = apolloClient.mutation(
                CreateStartupMutation(input = input)
            ).execute()
            if (response.hasErrors()) {
                Result.failure(Exception(response.errors?.first()?.message))
            } else {
                val startup = response.data?.createStartup
                if (startup != null) {
                    Result.success(startup)
                } else {
                    Result.failure(Exception("Failed to create startup"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }*/
}
