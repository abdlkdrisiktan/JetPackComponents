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
    @ColumnInfo(name = "word") val word: String
) : Serializable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}