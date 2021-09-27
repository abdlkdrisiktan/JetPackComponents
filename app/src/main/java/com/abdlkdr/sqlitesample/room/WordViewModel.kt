package com.abdlkdr.sqlitesample.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * Created by aisiktan on 27,September,2021
 */
class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    val allWordsById: LiveData<List<Word>> = repository.allWordsById.asLiveData()

    fun insert(word: Word) = viewModelScope.launch { repository.insertData(word = word) }

    fun delete(id: Int) = viewModelScope.launch { repository.deleteData(id = id) }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }

    fun updateData(word: Word) = viewModelScope.launch { repository.updateData(word = word) }

}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}