package com.abdlkdr.sqlitesample.room

import androidx.room.Entity

/**
 * Created by aisiktan on 24,September,2021
 */
@Entity(tableName = "word_table")
data class Word(val word: String) {
}