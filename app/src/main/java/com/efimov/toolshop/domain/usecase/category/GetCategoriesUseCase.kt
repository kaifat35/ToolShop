package com.efimov.toolshop.domain.usecase.category

import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategories()
    }
}