package com.example.androidsppechtotextrecognition.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText

@Dao
interface SpeechTextDao {

    @Insert
    fun insert(speechText: SpeechText)

    @Query("SELECT * FROM speechtext_table ")
    fun getAllText(): LiveData<List<SpeechText>>

    @Update
    fun updateText(speechText: SpeechText)

    @Query("SELECT * FROM speechtext_table WHERE id = :id")
    fun loadTextById(id: Int): SpeechText?
}