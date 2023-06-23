package com.xxmrk888ytxx.mydictionary.data.Repositoryes.ImageRepository

import android.net.Uri

interface ImageRepository {

    suspend fun addImage(imagePath:String) : String?

    suspend fun removeImage(imagePath: String)
}