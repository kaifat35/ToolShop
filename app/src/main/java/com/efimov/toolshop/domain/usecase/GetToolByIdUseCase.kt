package com.efimov.toolshop.domain.usecase

import com.efimov.toolshop.domain.model.Tool
import com.efimov.toolshop.domain.repository.ToolRepository
import javax.inject.Inject

class GetToolByIdUseCase @Inject constructor(
    private val repository: ToolRepository
) {
    suspend fun invoke(id: Int): Tool? {
        return repository.getToolById(id)
    }
}