package com.xxmrk888ytxx.mydictionary.glue.WordGroupScreen

import android.net.Uri
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.wordgroupscreen.contract.AttachImageContract
import javax.inject.Inject

class AttachImageContractImpl @Inject constructor(
    private val imageRepository: ImageRepository,
    private val wordGroupRepository: WordGroupRepository
) : AttachImageContract {

    override suspend fun attachImage(wordGroupId: Int, imageUri: Uri) {
        val imagePath = imageRepository.addImage(imageUri.toString())

        wordGroupRepository.updateImage(wordGroupId,imagePath)
    }
}