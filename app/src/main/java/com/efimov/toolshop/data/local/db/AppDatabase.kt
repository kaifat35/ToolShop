package com.efimov.toolshop.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.efimov.toolshop.data.local.entity.CartItemDbModel
import com.efimov.toolshop.data.local.entity.CategoryDbModel
import com.efimov.toolshop.data.local.entity.OrderDbModel
import com.efimov.toolshop.data.local.entity.ProductDbModel
import com.efimov.toolshop.data.local.entity.UserDbModel

@Database(
    entities = [
        CartItemDbModel::class,
        CategoryDbModel::class,
        ProductDbModel::class,
        UserDbModel::class,
        OrderDbModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): AppDao
}