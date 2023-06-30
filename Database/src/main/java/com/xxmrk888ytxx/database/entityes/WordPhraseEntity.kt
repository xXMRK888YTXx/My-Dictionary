package com.xxmrk888ytxx.database.entityes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "WordPhraseTable",
    indices = [Index("id", unique = true),Index("wordId")],
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
internal data class WordPhraseEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val wordId:Int,
    val phraseText:String,
    val translateText:String
)