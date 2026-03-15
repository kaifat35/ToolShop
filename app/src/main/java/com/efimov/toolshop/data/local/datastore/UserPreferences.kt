package com.efimov.toolshop.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.efimov.toolshop.data.repository.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val USER_ID = intPreferencesKey("user_id")
    }

    suspend fun saveAuth(token: String, userId: Int) {
        dataStore.edit { prefs ->
            prefs[TOKEN] = token
            prefs[USER_ID] = userId
        }
    }

    suspend fun clearAuth() {
        dataStore.edit { it.clear() }
    }

    fun getToken(): Flow<String?> = dataStore.data.map { it[TOKEN] }
    fun getUserId(): Flow<Int?> = dataStore.data.map { it[USER_ID] }
}