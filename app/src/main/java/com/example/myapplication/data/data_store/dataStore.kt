package com.example.myapplication.data.data_store

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.data.response.Data
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager @Inject constructor(@ApplicationContext val context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "user_prefs")
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val USER_DATA_KEY = stringPreferencesKey("user_data")
        val PROFILE_IMAGE_KEY = stringPreferencesKey("profile_image")

    }

    val getToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    val getProfileImage: Flow<String> = dataStore.data.map { preferences ->
        preferences[PROFILE_IMAGE_KEY] ?: ""
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token

        }
    }
    val getUserData: Flow<Data?> = dataStore.data.map { preferences ->
        preferences[USER_DATA_KEY]?.let { userJson ->
            Gson().fromJson(userJson, Data::class.java)
        }
    }
     suspend fun saveProfileImage(profileImageUri: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_IMAGE_KEY] = profileImageUri
        }
    }

    suspend fun clearToken() {
        Log.d("DataStoreManager", "Clearing token")
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
        Log.d("DataStoreManager", "Token cleared")
    }

    /** Save User Data **/
    suspend fun saveUserData(user: Data) {
        val userJson = Gson().toJson(user)
        dataStore.edit { preferences ->
            preferences[USER_DATA_KEY] = userJson
        }
    }

    /** Clear User Data **/
    suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(USER_DATA_KEY)
        }
    }
}
