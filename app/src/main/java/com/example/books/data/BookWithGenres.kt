package com.example.books.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BookWithGenres(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "genreId",
        associateBy = Junction(BookGenreCrossRef::class)
    )
    val genres: List<Genre>
)