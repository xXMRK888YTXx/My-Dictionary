package com.xxmrk888ytxx.mydictionary.glue.WordGroupScreen

import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.wordgroupscreen.contract.RemoveWordGroupContract
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveWordGroupContractImpl @Inject constructor(
    private val wordGroupRepository: WordGroupRepository,
    private val imageRepository: ImageRepository
) : RemoveWordGroupContract {

    override suspend fun removeWordGroup(wordGroupId: Int) {
        val wordGroupImageUrl = wordGroupRepository.getWordGroupById(wordGroupId).first().imageUrl

        wordGroupRepository.removeWordGroup(wordGroupId)

        if(wordGroupImageUrl != null)
            imageRepository.removeImage(wordGroupImageUrl)
    }
}