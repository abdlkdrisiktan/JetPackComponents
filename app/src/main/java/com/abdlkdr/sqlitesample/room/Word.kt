package com.abdlkdr.sqlitesample.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by aisiktan on 24,September,2021
 */
@Entity(tableName = "word_table")
data class Word(val idValue : Int ,  val wordValue: String) {
    @PrimaryKey(autoGenerate = true) val id : Int = idValue
    @ColumnInfo(name = "word") val word : String = wordValue
}