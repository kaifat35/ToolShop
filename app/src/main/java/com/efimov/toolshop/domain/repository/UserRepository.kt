package com.efimov.toolshop.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun saveAuth(token: String, userId: Int)

    suspend fun clearAuth()

    fun getToken(): Flow<String?>

    fun getUserId(): Flow<Int?>
}