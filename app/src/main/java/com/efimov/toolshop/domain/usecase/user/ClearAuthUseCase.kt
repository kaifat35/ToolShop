package com.efimov.toolshop.domain.usecase.user

import com.efimov.toolshop.domain.repository.UserRepository
import javax.inject.Inject

class ClearAuthUseCase  @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(token: String, userId: Int) {
        return repository.saveAuth(token,userId)
    }
}