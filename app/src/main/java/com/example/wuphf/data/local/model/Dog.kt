package com.example.wuphf.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class Dog(
    @PrimaryKey
    val message: String,
)