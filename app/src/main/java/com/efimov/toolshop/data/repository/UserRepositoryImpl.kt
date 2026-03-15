package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.datastore.UserPreferences
import com.efimov.toolshop.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userPreferences: UserPreferences
) : UserRepository {

    override suspend fun saveAuth(token: String, userId: Int) {
        userPreferences.saveAuth(token, userId)
    }

    override suspend fun clearAuth() {
        userPreferences.clearAuth()
    }

    override fun getToken(): Flow<String?> = userPreferences.getToken()

    override fun getUserId(): Flow<Int?> = userPreferences.getUserId()
}