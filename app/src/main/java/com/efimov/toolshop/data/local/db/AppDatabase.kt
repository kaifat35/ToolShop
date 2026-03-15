package com.efimov.toolshop.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.efimov.toolshop.data.local.entity.CartItemDbModel
import com.efimov.toolshop.data.local.entity.CategoryDbModel

@Database(
    entities = [
        CartItemDbModel::class,
        CategoryDbModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
}
