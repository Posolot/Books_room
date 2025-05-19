package com.example.books.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(
    entities = [Book::class, Genre::class, BookGenreCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "books.db"
                )
                    .openHelperFactory(SimpleSQLiteOpenHelperFactory(context))
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}
