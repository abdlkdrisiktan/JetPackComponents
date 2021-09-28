package com.abdlkdr.sqlitesample.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by aisiktan on 24,September,2021
 */
@Entity(tableName = "word_table")
data class Word(
    @ColumnInfo(name = "word") val word: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0



}