package com.example.androidsppechtotextrecognition.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsppechtotextrecognition.R
import com.example.androidsppechtotextrecognition.roomdb.AppExecutors
import com.example.androidsppechtotextrecognition.roomdb.SpeechTextDatabase
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText
import com.example.androidsppechtotextrecognition.ui.Constants
import kotlinx.android.synthetic.main.activity_edit.*

class UpdateActivity : AppCompatActivity() {

    private var textId = 0
    private lateinit var speechTextDatabase: SpeechTextDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        speechTextDatabase = SpeechTextDatabase.getInstance(applicationContext)!!
        if (intent != null && intent.hasExtra(Constants.UPDATE_TEXT_ID)) {
            textId = intent.getIntExtra(Constants.UPDATE_TEXT_ID, -1)

            AppExecutors.instance?.diskIO()?.execute(Runnable {
                val speechText = speechTextDatabase.speechTextDao().loadTextById(textId)
                etSpeechText.setText(speechText?.text.toString())
            })
        }

    }

    fun onUpdateClick(v: View?) {
        val speechText = SpeechText(etSpeechText.text.toString())

        AppExecutors.instance?.diskIO()?.execute(Runnable {
            speechText.id = textId
            speechTextDatabase.speechTextDao().updateText(speechText)
            finish()
        })
    }
}