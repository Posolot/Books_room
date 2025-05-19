package com.example.books.data

import androidx.room.Entity

@Entity(
    tableName = "book_genre_crossref",
    primaryKeys = ["bookId","genreId"]
)
data class BookGenreCrossRef(
    val bookId: Long,
    val genreId: Long
)