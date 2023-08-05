package com.xxmrk888ytxx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.dao.WordDao
import com.xxmrk888ytxx.database.dao.WordGroupDao
import com.xxmrk888ytxx.database.dao.WordPhraseDao
import com.xxmrk888ytxx.database.entityes.LanguageEntity
import com.xxmrk888ytxx.database.entityes.WordEntity
import com.xxmrk888ytxx.database.entityes.WordGroupEntity
import com.xxmrk888ytxx.database.entityes.WordPhraseEntity

@Database(
    version = 1,
    entities = [
        LanguageEntity::class,
        WordGroupEntity::class,
        WordEntity::class,
        WordPhraseEntity::class
    ]
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract val languageDao:LanguageDao

    abstract val wordGroupDao:WordGroupDao

    abstract val wordDao:WordDao

    abstract val wordPhraseDao: WordPhraseDao
}