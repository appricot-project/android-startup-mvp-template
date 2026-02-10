package ru.appricot.startuphub.auth

import android.content.Context
import android.net.Uri
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.core.net.toUri
import androidx.navigation3.ui.NavDisplay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.multibindings.IntoSet
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.browser.BrowserAllowList
import net.openid.appauth.browser.VersionedBrowserMatcher
import ru.appricot.navigation.Auth
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.navigation.Profile
import ru.appricot.startuphub.auth.confirmation.EmailConfirmationScreen
import ru.appricot.startuphub.auth.main.AuthChoiceScreen
import ru.appricot.startuphub.auth.signin.AuthConfig
import ru.appricot.startuphub.auth.signin.SignInScreen
import ru.appricot.startuphub.auth.signup.SignUpScreen
import ru.appricot.startuphub.authapi.EmailConfirmation
import ru.appricot.startuphub.authapi.SignIn
import ru.appricot.startuphub.authapi.SignUp
import ru.apprictor.startuphub.auth.BuildConfig

@Module
@InstallIn(ActivityRetainedComponent::class)
class AuthModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = { navigator ->
        entry<Auth>(
            metadata = NavDisplay.transitionSpec {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500),
                ) togetherWith ExitTransition.KeepUntilTransitionsFinished
            } + NavDisplay.popTransitionSpec {
                EnterTransition.None togetherWith
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(1000),
                    )
            } + NavDisplay.predictivePopTransitionSpec {
                EnterTransition.None togetherWith
                    slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(1000),
                    )
            },
        ) {
            AuthChoiceScreen(
                onLoginClick = {
                    navigator.navigate(SignIn)
                },
                onRegisterClick = { navigator.navigate(SignUp()) },
            )
        }
        entry<SignIn> {
            SignInScreen(
                onNavigateToCode = { email -> navigator.navigate(EmailConfirmation(email)) },
                onBackClick = { navigator.goBack() },
                onSuccessAuthorizationFlow = {
                    navigator.replace(Profile)
                },
            )
        }
        entry<SignUp> {
            SignUpScreen(
                onSuccessRegistration = {
                    navigator.goBack()
                    navigator.goBack()
                },
                onBackClick = { navigator.goBack() },
            )
        }
        entry<EmailConfirmation> { destination ->
            EmailConfirmationScreen(
                email = destination.email,
                onBackClick = { navigator.goBack() },
                onConfirmationSuccess = {
                    navigator.goBack()
                },
            )
        }
    }

    @Provides
    fun authorizationService(@ApplicationContext context: Context): AuthorizationService = AuthorizationService(
        context,
        AppAuthConfiguration.Builder()
            .setBrowserMatcher(
                BrowserAllowList(
                    VersionedBrowserMatcher.CHROME_CUSTOM_TAB,
                ),
            )
            .build(),
    )

    @Provides
    fun authConfig(): AuthConfig = object : AuthConfig {
        override fun authUri(): Uri = "https://id.appricot.ru/realms/foundation/protocol/openid-connect/auth".toUri()

        override fun tokenUri(): Uri = "https://id.appricot.ru/realms/foundation/protocol/openid-connect/token".toUri()

        override fun responseType(): String = ResponseTypeValues.CODE

        override fun scope(): String = "openid profile email"

        override fun clientId(): String = "PitchDeck_App"

        override fun clientSecret(): String = BuildConfig.KEYCLOAK_SECRET

        override fun callbackUri(): Uri = "pitchdeck://auth/callback".toUri()
    }
}
