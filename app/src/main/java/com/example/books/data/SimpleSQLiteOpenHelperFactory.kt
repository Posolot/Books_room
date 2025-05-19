package com.example.books.data

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import java.io.FileOutputStream

class SimpleSQLiteOpenHelperFactory(private val context: Context) : SupportSQLiteOpenHelper.Factory {
    override fun create(
        configuration: SupportSQLiteOpenHelper.Configuration
    ): SupportSQLiteOpenHelper {
        val delegate = FrameworkSQLiteOpenHelperFactory()
            .create(configuration)

        return object : SupportSQLiteOpenHelper by delegate {
            fun getReadableDatabase(): SupportSQLiteDatabase {
                copyDatabaseIfNeeded(configuration.name!!)
                return delegate.readableDatabase
            }
            fun getWritableDatabase(): SupportSQLiteDatabase {
                copyDatabaseIfNeeded(configuration.name!!)
                return delegate.writableDatabase
            }

            private fun copyDatabaseIfNeeded(dbName: String) {
                val dbFile = context.getDatabasePath(dbName)
                if (!dbFile.exists()) {
                    dbFile.parentFile?.mkdirs()
                    context.resources.openRawResource(
                        context.resources.getIdentifier(
                            "books", "raw", context.packageName
                        )
                    ).use { input ->
                        FileOutputStream(dbFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        }
    }
}