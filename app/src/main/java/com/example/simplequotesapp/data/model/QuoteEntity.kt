package com.example.simplequotesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_quotes")
data class Quote(
    @PrimaryKey val q: String,
    val a: String
)


