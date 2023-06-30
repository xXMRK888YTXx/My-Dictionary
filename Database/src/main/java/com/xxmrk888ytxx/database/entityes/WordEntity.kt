package com.xxmrk888ytxx.database.entityes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "WordsTable",
    indices = [
        Index("id", unique = true),
        Index("wordGroupId")
    ],
    foreignKeys = [
        ForeignKey(
            entity = WordGroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordGroupId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
internal data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val wordGroupId: Int,
    val wordText: String,
    val translateText: String,
    val transcriptionText: String,
)