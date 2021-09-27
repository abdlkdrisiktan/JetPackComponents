package com.abdlkdr.sqlitesample.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by aisiktan on 24,September,2021
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords() : Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Query("SELECT * FROM word_table ORDER BY id DESC")
    fun getWordsById() : Flow<List<Word>>

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Delete(entity = Word::class)
    suspend fun delete(id : Int)

    @Update(entity = Word::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(newWord: Word)
}