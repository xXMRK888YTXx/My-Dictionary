package com.xxmrk888ytxx.database.entityes

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Languages",
    indices = [Index("id", unique = true)]
)
internal data class LanguageEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name:String
)