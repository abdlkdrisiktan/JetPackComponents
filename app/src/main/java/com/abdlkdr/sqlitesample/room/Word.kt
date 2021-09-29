package com.abdlkdr.sqlitesample.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by aisiktan on 24,September,2021
 */
@Entity(tableName = "word_table")
data class Word (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "word") val word: String
) : Serializable {}