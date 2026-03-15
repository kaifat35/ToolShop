package com.efimov.toolshop.domain.usecase.user

import com.efimov.toolshop.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Int?> {
        return repository.getUserId()
    }
}