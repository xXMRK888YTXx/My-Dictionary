package com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository

import android.net.Uri

interface ImageRepository {

    suspend fun addImage(imagePath:String) : String?

    suspend fun removeImage(imagePath: String)
}