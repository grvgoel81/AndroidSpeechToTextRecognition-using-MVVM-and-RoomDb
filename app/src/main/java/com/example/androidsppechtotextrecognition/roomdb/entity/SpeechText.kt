package com.example.androidsppechtotextrecognition.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "speechtext_table")
data class SpeechText(

    var text: String

) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}