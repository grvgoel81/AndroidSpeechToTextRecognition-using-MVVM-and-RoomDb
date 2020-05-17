package com.example.androidsppechtotextrecognition.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsppechtotextrecognition.R
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText
import com.example.androidsppechtotextrecognition.ui.Constants
import com.example.androidsppechtotextrecognition.ui.activity.UpdateActivity

class TextAdapter(private val speechText: List<SpeechText>, private val context: Context) :
    RecyclerView.Adapter<TextAdapter.TextHolder>(), Filterable {

    private var dataSearchList: List<SpeechText>? = null

    init {
        this.dataSearchList = speechText
    }

    inner class TextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSpeechText: TextView = itemView.findViewById(R.id.tvSpeechText)
        var ivEdit: ImageView = itemView.findViewById(R.id.ivEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return TextHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataSearchList?.size!!
    }

    override fun onBindViewHolder(holder: TextHolder, position: Int) {
        val speechText = dataSearchList?.get(position)
        holder.tvSpeechText.text = speechText!!.text

        holder.ivEdit.setOnClickListener {
            val textId: Int = dataSearchList?.get(position)!!.id
            val i = Intent(context, UpdateActivity::class.java)
            i.putExtra(Constants.UPDATE_TEXT_ID, textId)
            context.startActivity(i)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                dataSearchList = if (charString.isEmpty()) {
                    speechText
                } else {
                    val filteredList = ArrayList<SpeechText>()
                    for (data in speechText) {
                        if (data.text!!.toUpperCase().contains(charString.toUpperCase())) {
                            filteredList.add(data)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = dataSearchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                dataSearchList = filterResults.values as ArrayList<SpeechText>
                notifyDataSetChanged()
            }
        }
    }

}