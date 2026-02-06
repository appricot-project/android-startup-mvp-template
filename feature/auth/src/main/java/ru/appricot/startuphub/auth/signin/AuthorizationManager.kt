package ru.appricot.startuphub.auth.signin

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthorizationManager @Inject constructor(private val authorizationService: AuthorizationService) {
    fun provideAuthorizationIntent(): Intent {
        val authRequest =
            AuthorizationRequest.Builder(
                AuthorizationServiceConfiguration(
                    AuthConfig.AUTH_URI,
                    AuthConfig.TOKEN_URI,
                ),
                AuthConfig.CLIENT_ID,
                AuthConfig.RESPONSE_TYPE,
                AuthConfig.CALLBACK_URL,
            )
                .setScope(AuthConfig.SCOPE)
                .setRedirectUri(AuthConfig.CALLBACK_URL)
                .build()

        val customTabsIntent = CustomTabsIntent.Builder().build()

        return authorizationService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent,
        )
    }

    fun handleAuthResponseIntent(intent: Intent?, onComplete: (Result<AuthState>) -> Unit) {
        if (intent == null) return
        val authResp = AuthorizationResponse.fromIntent(intent)
        val exception = AuthorizationException.fromIntent(intent)
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            exception != null -> onComplete(Result.failure(exception))

            tokenExchangeRequest != null -> {
                performTokenRequest(
                    authorizationService,
                    tokenExchangeRequest,
                    AuthState(authResp, exception),
                    onComplete = { onComplete(Result.success(it)) },
                    onError = { exception ->
                        exception?.let { onComplete(Result.failure(it)) }
                    },
                )
            }
        }
    }

    fun dispose() {
        authorizationService.dispose()
    }

    private fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        authState: AuthState,
        onComplete: (AuthState) -> Unit,
        onError: (Exception?) -> Unit,
    ) {
        authService.performTokenRequest(
            tokenRequest,
            ClientSecretPost(AuthConfig.CLIENT_SECRET),
        ) { response, ex ->
            when {
                response != null -> {
                    authState.update(response, ex)
                    onComplete(authState)
                }

                else -> onError(ex)
            }
        }
    }
}
