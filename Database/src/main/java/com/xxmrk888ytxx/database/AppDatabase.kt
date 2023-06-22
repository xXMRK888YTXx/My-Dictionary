package com.xxmrk888ytxx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxmrk888ytxx.database.dao.LanguageDao
import com.xxmrk888ytxx.database.entityes.LanguageEntity

@Database(
    version = 1,
    entities = [
        LanguageEntity::class
    ]
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract val languageDao:LanguageDao
}