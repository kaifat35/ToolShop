package com.efimov.toolshop.domain.usecase.category

import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<Category> {
        return repository.getCategories()
    }
}