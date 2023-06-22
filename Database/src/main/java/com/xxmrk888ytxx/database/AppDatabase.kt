package com.xxmrk888ytxx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.dao.WordGroupDao
import com.xxmrk888ytxx.database.entityes.LanguageEntity
import com.xxmrk888ytxx.database.entityes.WordGroupEntity

@Database(
    version = 1,
    entities = [
        LanguageEntity::class,
        WordGroupEntity::class
    ]
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract val languageDao:LanguageDao

    abstract val wordGroupDao:WordGroupDao
}