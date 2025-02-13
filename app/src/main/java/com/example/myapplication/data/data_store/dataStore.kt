package com.example.myapplication.data.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {
    private val dataStore = context.dataStore
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_prefs")
        val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    val getToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }
    fun getAuthToken(): StateFlow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }.stateIn(
            CoroutineScope(Dispatchers.IO),
            SharingStarted.WhileSubscribed(3000),
            null
        )
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
}