package com.example.books.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GenreWithBooks(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "bookId",
        associateBy = Junction(BookGenreCrossRef::class)
    )
    val books: List<Book>
)