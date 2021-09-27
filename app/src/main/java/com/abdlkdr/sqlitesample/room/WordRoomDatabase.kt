package com.abdlkdr.sqlitesample.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by aisiktan on 27,September,2021
 */
/**
 * When you modify the database schema, you'll need to update the version number and define a migration strategy.
 */
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    // Create Singleton Database to prevent having multiple instances of the database opened at the same time
    companion object {
        private var INSTANCE: WordRoomDatabase? = null
        fun getDatabase(context: Context) {
            return (INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
// Add migrations with complex queries https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
//                    .addMigrations()
                    .build()
                INSTANCE = instance
                instance
            }) as Unit
        }
    }


}