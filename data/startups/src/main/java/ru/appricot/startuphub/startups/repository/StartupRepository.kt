package ru.appricot.startuphub.startups.repository

import ru.appricot.startuphub.startups.model.StartupModel
import ru.appricot.startuphub.startups.source.RemoteStartupDataSource
import ru.appricot.startuphub.startups.source.RemoteStartupsDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartupRepository @Inject constructor(
    private val remoteDataStartupsRepository: RemoteStartupsDataSource,
    private val remoteDataStartupRepository: RemoteStartupDataSource,
) {

    suspend fun getStartups(): List<StartupModel> = remoteDataStartupsRepository.getStartups()

    suspend fun getStartup(id: String) = remoteDataStartupRepository.getStartup(id)

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
