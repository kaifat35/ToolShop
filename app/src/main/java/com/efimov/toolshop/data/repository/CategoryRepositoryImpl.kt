package com.efimov.toolshop.data.repository

import com.efimov.toolshop.data.local.db.AppDao
import com.efimov.toolshop.data.local.entity.CategoryDbModel
import com.efimov.toolshop.data.local.mapper.toEntity
import com.efimov.toolshop.data.remove.ApiService
import com.efimov.toolshop.domain.model.Category
import com.efimov.toolshop.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: AppDao,
    private val api: ApiService
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return runCatching {
            val remote = api.getCategories()
            dao.insertCategories(remote.map { CategoryDbModel(it.id, it.name, it.parentId) })
            remote
        }.getOrElse {
            dao.getCategories().map { it.toEntity() }
        }
    }
}