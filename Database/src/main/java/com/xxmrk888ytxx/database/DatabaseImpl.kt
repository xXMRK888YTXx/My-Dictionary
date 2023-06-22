package com.xxmrk888ytxx.database

import android.content.Context
import androidx.room.Room
import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.dao.WordGroupDao
import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSource
import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSourceImpl
import com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource.WordGroupLocalDataSource
import com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource.WordGroupLocalDataSourceImpl

internal class DatabaseImpl(private val context: Context) : Database {

    private val appDatabase:AppDatabase by lazy {
        Room.databaseBuilder(context,AppDatabase::class.java,"database.db")
            .build()
    }

    private val languageDao:LanguageDao by lazy {
        appDatabase.languageDao
    }

    private val wordGroupDao:WordGroupDao by lazy {
        appDatabase.wordGroupDao
    }

    override val languageLocalDataSource: LanguageLocalDataSource by lazy {
        LanguageLocalDataSourceImpl(languageDao)
    }
    override val wordGroupLocalDataSource: WordGroupLocalDataSource by lazy {
        WordGroupLocalDataSourceImpl(languageDao,wordGroupDao)
    }


}