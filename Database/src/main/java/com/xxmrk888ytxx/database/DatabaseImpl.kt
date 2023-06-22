package com.xxmrk888ytxx.database

import android.content.Context
import androidx.room.Room
import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSource
import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSourceImpl

internal class DatabaseImpl(private val context: Context) : Database {

    private val appDatabase:AppDatabase by lazy {
        Room.databaseBuilder(context,AppDatabase::class.java,"database.db")
            .build()
    }

    private val languageDao:LanguageDao by lazy {
        appDatabase.languageDao
    }
    override val languageLocalDataSource: LanguageLocalDataSource by lazy {
        LanguageLocalDataSourceImpl(languageDao)
    }


}