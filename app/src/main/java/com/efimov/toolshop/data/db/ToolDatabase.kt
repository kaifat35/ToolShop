package com.efimov.toolshop.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.efimov.toolshop.data.entity.ToolEntity


@Database(
    entities = [ToolEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToolDatabase : RoomDatabase() {
    abstract fun toolDao(): ToolDao
}