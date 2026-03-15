package com.efimov.toolshop.domain.repository

import com.efimov.toolshop.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}