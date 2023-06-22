package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.database.Database
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideDatabase(context: Context) : Database {
        return Database.create(context)
    }

    @Provides
    @AppScope
    fun provideLanguageLocalDataSource(database: Database) = database.languageLocalDataSource
}