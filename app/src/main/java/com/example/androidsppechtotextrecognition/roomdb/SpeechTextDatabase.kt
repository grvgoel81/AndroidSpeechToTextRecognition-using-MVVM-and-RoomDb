package com.example.androidsppechtotextrecognition.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidsppechtotextrecognition.roomdb.dao.SpeechTextDao
import com.example.androidsppechtotextrecognition.roomdb.entity.SpeechText

@Database(entities = [SpeechText::class], version = 2)
abstract class SpeechTextDatabase : RoomDatabase() {

    abstract fun speechTextDao(): SpeechTextDao

    companion object {
        private var instance: SpeechTextDatabase? = null

        fun getInstance(context: Context): SpeechTextDatabase? {
            if (instance == null) {
                synchronized(SpeechTextDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SpeechTextDatabase::class.java, "SpeechText_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }

    }

}