package com.xxmrk888ytxx.mydictionary.glue.WordGroupScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.wordgroupscreen.contract.RemoveImageContract
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveImageContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository,
    private val imageRepository: ImageRepository
) : RemoveImageContract {

    override suspend fun removeImage(wordGroup: Int) {
        val imageUrl = wordGroupRepository.getWordGroupById(wordGroup).first().imageUrl ?: return

        imageRepository.removeImage(imageUrl)
        wordGroupRepository.updateImage(wordGroup,null)
    }
}