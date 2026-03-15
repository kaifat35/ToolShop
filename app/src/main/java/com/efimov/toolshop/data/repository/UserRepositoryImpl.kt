package com.efimov.toolshop.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.efimov.toolshop.data.local.datastore.UserPreferences
import com.efimov.toolshop.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserRepositoryImpl@Inject constructor(
    private val userPreferences: UserPreferences
) : UserRepository {

    override suspend fun saveAuth(token: String, userId: Int) {
        return userPreferences.saveAuth(token,userId)
    }

    override suspend fun clearAuth() {
        return userPreferences.clearAuth()
    }

    override fun getToken(): Flow<String?> {
        return userPreferences.getToken()
    }

    override fun getUserId(): Flow<Int?> {
        return userPreferences.getUserId()
    }
}