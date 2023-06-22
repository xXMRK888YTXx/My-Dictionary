package com.xxmrk888ytxx.database

import android.content.Context
import com.xxmrk888ytxx.database.dataSource.LanguageLocalDataSource.LanguageLocalDataSource

interface Database {

    val languageLocalDataSource: LanguageLocalDataSource

    companion object {
        fun create(context: Context) : Database {
            return DatabaseImpl(context)
        }
    }
}