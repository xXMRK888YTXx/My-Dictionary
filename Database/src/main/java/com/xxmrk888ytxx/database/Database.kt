package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSource
import com.xxmrk888ytxx.database.dataSource.WordGroupLocalDataSource.WordGroupLocalDataSource

interface Database {

    val languageLocalDataSource: LanguageLocalDataSource

    val wordGroupLocalDataSource:WordGroupLocalDataSource

    companion object {
        fun create(context: Context) : Database {
            return DatabaseImpl(context)
        }
    }
}