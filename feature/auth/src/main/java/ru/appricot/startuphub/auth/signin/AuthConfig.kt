package ru.appricot.startuphub.auth.signin

import androidx.core.net.toUri
import net.openid.appauth.ResponseTypeValues
import ru.apprictor.startuphub.auth.BuildConfig

object AuthConfig {
    const val CLIENT_ID = "PitchDeck_App"
    const val CLIENT_SECRET = BuildConfig.KEYCLOAK_SECRET
    val CALLBACK_URL = "pitchdeck://auth/callback".toUri()

    val AUTH_URI = "https://id.appricot.ru/realms/foundation/protocol/openid-connect/auth".toUri() // account-service
    val TOKEN_URI = "https://id.appricot.ru/realms/foundation/protocol/openid-connect/token".toUri() // token-service
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "openid profile email"
}
