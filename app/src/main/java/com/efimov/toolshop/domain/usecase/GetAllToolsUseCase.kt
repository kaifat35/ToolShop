package com.efimov.toolshop.domain.usecase

import com.efimov.toolshop.domain.model.Tool
import com.efimov.toolshop.domain.repository.ToolRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllToolsUseCase @Inject constructor(
    private val repository: ToolRepository
) {
    fun invoke(): Flow<List<Tool>> {
        return repository.getAllTools()
    }
}