package com.abdlkdr.sqlitesample.room

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * Created by aisiktan on 27,September,2021
 */
class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val searchWord: MutableLiveData<List<Word>> = MutableLiveData<List<Word>>()

    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch { repository.insertData(word = word) }

    fun delete(word: Word) = viewModelScope.launch { repository.deleteData(word = word) }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }

    fun updateData(word: Word) = viewModelScope.launch { repository.updateData(word = word) }

    fun findByWord(word: String) {
        val value =  repository.findByWord(query = word).asLiveData()
        searchWord.value = value.value
    }
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