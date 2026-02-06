package ru.appricot.startuphub.auth

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.appricot.startuphub.auth.source.SignUpRequest

interface AuthApi {
    @Headers("X-Requested-With: XMLHttpRequest")
    @POST("user")
    suspend fun register(@Body body: SignUpRequest): Boolean
}
