package com.example.books.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey val genreId: Long,
    val name: String
)