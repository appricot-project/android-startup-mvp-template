package ru.appricot.startuphub.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.RegistrationResponse
import net.openid.appauth.TokenResponse
import org.json.JSONException
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStateManager @Inject constructor(private val dataStore: DataStore<Preferences>) {
    companion object {
        val KEY_AUTH_STATE = stringPreferencesKey("auth-state")
    }

    private val mPrefsLock: ReentrantLock = ReentrantLock()
    private val mCurrentAuthState: AtomicReference<AuthState> = AtomicReference()

    fun getCurrent(): AuthState {
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

    fun replace(state: AuthState): AuthState {
        writeState(state)
        mCurrentAuthState.set(state)
        return state
    }

    fun updateAfterAuthorization(response: AuthorizationResponse?, ex: AuthorizationException?): AuthState {
        val current = getCurrent()
        current.update(response, ex)
        return replace(current)
    }

    fun updateAfterTokenResponse(response: TokenResponse?, ex: AuthorizationException?): AuthState {
        val current = getCurrent()
        current.update(response, ex)
        return replace(current)
    }

    fun updateAfterRegistration(response: RegistrationResponse?, ex: AuthorizationException?): AuthState {
        val current = getCurrent()
        if (ex != null) {
            return current
        }

        current.update(response)
        return replace(current)
    }

    private fun readState(): AuthState {
        mPrefsLock.lock()
        try {
            val currentState: String? = runBlocking { dataStore.data.map { it[KEY_AUTH_STATE] }.firstOrNull() }
            if (currentState == null) {
                return AuthState()
            }

            try {
                return AuthState.jsonDeserialize(currentState)
            } catch (ex: JSONException) {
                Timber.w("Failed to deserialize stored auth state - discarding")
                return AuthState()
            }
        } finally {
            mPrefsLock.unlock()
        }
    }

    private fun writeState(state: AuthState?) {
        mPrefsLock.lock()
        try {
            runBlocking {
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
        } finally {
            mPrefsLock.unlock()
        }
    }
}
