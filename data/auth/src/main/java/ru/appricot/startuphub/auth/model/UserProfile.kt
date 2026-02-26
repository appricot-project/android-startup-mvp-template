package ru.appricot.startuphub.auth.model

import android.util.Base64
import com.google.gson.Gson
import kotlinx.serialization.Serializable
import javax.inject.Inject
import javax.inject.Singleton

data class UserProfile(val firstName: String = "", val lastName: String = "", val email: String = "") {
    interface Factory {
        fun create(idToken: String): UserProfile
    }
}

@Singleton
class UserProfileFactory @Inject constructor() : UserProfile.Factory {
    override fun create(idToken: String): UserProfile {
        val dto = parseJwtPayload(idToken)
        return UserProfile(
            firstName = dto.given_name.orEmpty(),
            lastName = dto.family_name.orEmpty(),
            email = dto.email.orEmpty(),
        )
    }

    fun parseJwtPayload(token: String): JwtPayload {
        val payload = token.split(".")[1]
        val decoded = Base64.decode(
            payload,
            Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING,
        )
        return Gson().fromJson(String(decoded), JwtPayload::class.java)
    }
}

@Serializable
data class JwtPayload(
    val given_name: String? = null,
    val family_name: String? = null,
    val name: String? = null,
    val email: String? = null,
    val scope: String? = null,
)
