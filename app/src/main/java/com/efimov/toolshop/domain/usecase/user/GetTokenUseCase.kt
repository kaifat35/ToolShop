package com.efimov.toolshop.domain.usecase.user

import com.efimov.toolshop.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<String?> {
        return repository.getToken()
    }
}