package com.example.books.data

import androidx.room.*

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAllBooks(): List<Book>

    @Query("SELECT * FROM genres")
    fun getAllGenres(): List<Genre>

    @Transaction
    @Query("SELECT * FROM books WHERE bookId = :id")
    fun getBookWithGenres(id: Long): BookWithGenres

    @Transaction
    @Query("SELECT * FROM genres WHERE genreId = :id")
    fun getGenreWithBooks(id: Long): GenreWithBooks
}