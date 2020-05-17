package com.example.androidsppechtotextrecognition.ui.activity

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsppechtotextrecognition.R
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText
import com.example.androidsppechtotextrecognition.ui.adapter.TextAdapter
import com.example.androidsppechtotextrecognition.viewmodel.TextViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100
    private lateinit var textViewModel: TextViewModel
    private lateinit var textAdapter: TextAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mLayoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recyclerView.isNestedScrollingEnabled = false

        setUpViewModel()
        setUpSearch()
    }

    private fun setUpViewModel() {
        textViewModel = ViewModelProviders.of(this).get(TextViewModel::class.java)
        textViewModel.getAllText().observe(this,
            Observer<List<SpeechText>> { t -> setupRecyclerView(t) })
    }

    private fun setupRecyclerView(results: List<SpeechText>?) {

        if (results != null) {
            textAdapter = TextAdapter(results, this@MainActivity)
            recyclerView.adapter = textAdapter
            textAdapter?.notifyDataSetChanged()
        }
    }

    private fun setUpSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                textAdapter!!.filter.filter(editable)
            }
        })
    }

    fun onButtonClick(v: View?) {
        val mSpeechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mSpeechIntent.putExtra(
            RecognizerIntent.EXTRA_CALLING_PACKAGE,
            this.packageName
        )
        try {
            startActivityForResult(mSpeechIntent, REQUEST_CODE)
        } catch (a: ActivityNotFoundException) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    textViewModel.insert(SpeechText(result[0]))
                }
            }
        }
    }
}
