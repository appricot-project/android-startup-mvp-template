package ru.appricot.startuphub.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.RegistrationResponse
import net.openid.appauth.TokenResponse
import org.json.JSONException
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStateManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val KEY_AUTH_STATE = stringPreferencesKey("auth-state")
    }

    private val mPrefsLock = Mutex()
    private val mCurrentAuthState: AtomicReference<AuthState> = AtomicReference()

    suspend fun getCurrent(): AuthState {
        if (mCurrentAuthState.get() != null) {
            return mCurrentAuthState.get()
        }

        val state: AuthState = readState()
        if (mCurrentAuthState.compareAndSet(null, state)) {
            return state
        } else {
            return mCurrentAuthState.get()
        }
    }

    fun authState(): Flow<AuthState?> = dataStore.data.map { preferences ->
        preferences[KEY_AUTH_STATE]?.let {
            AuthState.jsonDeserialize(it)
        }
    }

    suspend fun replace(state: AuthState): AuthState {
        writeState(state)
        mCurrentAuthState.set(state)
        return state
    }

    suspend fun updateAfterAuthorization(response: AuthorizationResponse?, ex: AuthorizationException?): AuthState {
        val current = getCurrent()
        current.update(response, ex)
        return replace(current)
    }

    suspend fun updateAfterTokenResponse(response: TokenResponse?, ex: AuthorizationException?): AuthState {
        val current = getCurrent()
        current.update(response, ex)
        return replace(current)
    }

    suspend fun updateAfterRegistration(response: RegistrationResponse?, ex: AuthorizationException?): AuthState {
        val current = getCurrent()
        if (ex != null) {
            return current
        }

        current.update(response)
        return replace(current)
    }

    private suspend fun readState(): AuthState {
        mPrefsLock.withLock {
            val currentState: String? = dataStore.data.map { it[KEY_AUTH_STATE] }.firstOrNull()
            if (currentState == null) {
                return AuthState()
            }

            try {
                return AuthState.jsonDeserialize(currentState)
            } catch (ex: JSONException) {
                Timber.w("Failed to deserialize stored auth state - discarding")
                return AuthState()
            }
        }
    }

    private suspend fun writeState(state: AuthState?) {
        mPrefsLock.withLock {
            dataStore.updateData {
                it.toMutablePreferences().also { preferences ->
                    if (state == null) {
                        preferences.remove(KEY_AUTH_STATE)
                    } else {
                        preferences[KEY_AUTH_STATE] = state.jsonSerializeString()
                    }
                }
            }
        }
    }
}
