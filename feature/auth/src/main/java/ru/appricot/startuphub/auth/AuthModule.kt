package ru.appricot.startuphub.auth

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.multibindings.IntoSet
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationService
import net.openid.appauth.browser.BrowserAllowList
import net.openid.appauth.browser.VersionedBrowserMatcher
import ru.appricot.navigation.Auth
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.startuphub.auth.confirmation.EmailConfirmationScreen
import ru.appricot.startuphub.auth.main.AuthChoiceScreen
import ru.appricot.startuphub.auth.signin.SignInScreen
import ru.appricot.startuphub.auth.signup.SignUpScreen
import ru.appricot.startuphub.authapi.EmailConfirmation
import ru.appricot.startuphub.authapi.SignIn
import ru.appricot.startuphub.authapi.SignUp

@Module
@InstallIn(ActivityRetainedComponent::class)
class AuthModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(): EntryProviderInstaller = { navigator ->
        entry<Auth> {
            AuthChoiceScreen(
                onLoginClick = { navigator.navigate(SignIn) },
                onRegisterClick = { navigator.navigate(SignUp()) },
            )
        }
        entry<SignIn> {
            SignInScreen(
                onNavigateToCode = { email -> navigator.navigate(EmailConfirmation(email)) },
                onBackClick = { navigator.goBack() },
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
}
