package com.efimov.toolshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val avatarUrl: String? = null
)
