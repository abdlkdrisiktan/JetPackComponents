package com.abdlkdr.sqlitesample.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * Created by aisiktan on 27,September,2021
 */
class WordRepository(private val wordDao: WordDao) {

    val allWordsById : Flow<List<Word>> = wordDao.getWordsById()

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // Room executes suspend queries off the main thread.
    @WorkerThread
    suspend fun insertData(word: Word) {
        wordDao.insert(word= word)
    }

    @WorkerThread
    suspend fun deleteData(id : Int) {
        wordDao.delete(id = id)
    }

    @WorkerThread
    suspend fun updateData(word: Word) {
        wordDao.update(newWord = word)
    }

    @WorkerThread
    suspend fun deleteAll(){
        wordDao.deleteAll()
    }
}