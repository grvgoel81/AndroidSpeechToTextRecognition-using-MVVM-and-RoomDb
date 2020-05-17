package com.example.androidsppechtotextrecognition.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.androidsppechtotextrecognition.repository.TextRepository
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText

class TextViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TextRepository =
        TextRepository(application)

    private var allText: LiveData<List<SpeechText>> = repository.getAllText()

    fun insert(speechText: SpeechText) {
        repository.insert(speechText)
    }

    fun getAllText(): LiveData<List<SpeechText>> {
        return allText
    }
}