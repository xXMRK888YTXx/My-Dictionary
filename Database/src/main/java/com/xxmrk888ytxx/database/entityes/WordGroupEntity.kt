package com.xxmrk888ytxx.database.entityes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "WordGroups",
    indices = [Index(
        "id",
        unique = true
    ), Index("primaryLanguageId"), Index("secondaryLanguageId")],
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["primaryLanguageId"],
            onDelete = RESTRICT
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["secondaryLanguageId"],
            onDelete = RESTRICT
        )
    ]
)
internal data class WordGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val primaryLanguageId: Int,
    val secondaryLanguageId: Int,
    val imageUrl:String?
)
