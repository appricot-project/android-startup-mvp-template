package ru.appricot.startuphub.auth

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import ru.appricot.navigation.EntryProviderInstaller
import ru.appricot.startuphub.auth.confirmation.EmailConfirmationScreen
import ru.appricot.startuphub.auth.main.AuthChoiceScreen
import ru.appricot.startuphub.auth.signin.SignInScreen
import ru.appricot.startuphub.authapi.Auth
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
}
