package ru.appricot.startuphub.auth.signin

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.TokenRequest
import ru.appricot.startuphub.auth.AuthStateManager
import javax.inject.Inject

class AuthorizationManager @Inject constructor(
    private val authorizationService: AuthorizationService,
    private val authStateManager: AuthStateManager,
    private val authConfig: AuthConfig,
) {
    fun provideAuthorizationIntent(): Intent {
        val authRequest =
            AuthorizationRequest.Builder(
                AuthorizationServiceConfiguration(
                    authConfig.authUri(),
                    authConfig.tokenUri(),
                ),
                authConfig.clientId(),
                authConfig.responseType(),
                authConfig.callbackUri(),
            )
                .setScope(authConfig.scope())
                .setRedirectUri(authConfig.callbackUri())
                .build()

        val customTabsIntent = CustomTabsIntent.Builder().build()

        return authorizationService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent,
        )
    }

    suspend fun handleAuthResponseIntent(intent: Intent?, onComplete: (Result<AuthState>) -> Unit) {
        if (intent == null) return
        coroutineScope {
            val authResp = AuthorizationResponse.fromIntent(intent)
            val exception = AuthorizationException.fromIntent(intent)
            val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
                ?.createTokenExchangeRequest()
            authStateManager.updateAfterAuthorization(authResp, exception)
            when {
                exception != null -> onComplete(Result.failure(exception))

                tokenExchangeRequest != null -> {
                    performTokenRequest(
                        authorizationService,
                        tokenExchangeRequest,
                        onComplete = {
                            onComplete(Result.success(it))
                        },
                        onError = { exception ->
                            exception?.let { onComplete(Result.failure(it)) }
                        },
                    )
                }
            }
        }
    }

    fun dispose() {
        authorizationService.dispose()
    }

    private suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: (AuthState) -> Unit,
        onError: (Exception?) -> Unit,
    ) = coroutineScope {
        authService.performTokenRequest(
            tokenRequest,
            ClientSecretPost(authConfig.clientSecret()),
        ) { response, ex ->
            when {
                response != null -> {
                    runBlocking {
                        authStateManager.updateAfterTokenResponse(response, ex)
                        onComplete(authStateManager.getCurrent())
                    }
                }

                else -> onError(ex)
            }
        }
    }
}
