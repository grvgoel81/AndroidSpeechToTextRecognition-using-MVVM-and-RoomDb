package com.example.androidsppechtotextrecognition.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.androidsppechtotextrecognition.roomdb.SpeechTextDatabase
import com.example.androidsppechtotextrecognition.roomdb.dao.SpeechTextDao
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText

class TextRepository(application: Application) {

    private var speechTextDao: SpeechTextDao

    private var allText: LiveData<List<SpeechText>>

    init {
        val database: SpeechTextDatabase = SpeechTextDatabase.getInstance(
            application.applicationContext
        )!!
        speechTextDao = database.speechTextDao()
        allText = speechTextDao.getAllText()
    }

    fun insert(speechText: SpeechText) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(speechTextDao).execute(speechText)
    }

    fun getAllText(): LiveData<List<SpeechText>> {
        return allText
    }

    private class InsertNoteAsyncTask(speechTextDao: SpeechTextDao) :
        AsyncTask<SpeechText, Unit, Unit>() {
        val speechTextDao = speechTextDao

        override fun doInBackground(vararg p0: SpeechText?) {
            speechTextDao.insert(p0[0]!!)
        }
    }
}