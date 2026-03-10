package com.efimov.toolshop.domain.usecase

import com.efimov.toolshop.domain.repository.ToolRepository
import javax.inject.Inject

class DeleteToolCartUseCase @Inject constructor(
    private val repository: ToolRepository
) {
    suspend fun invoke(id: Int) {
        return repository.deleteToolCart(id)
    }
}