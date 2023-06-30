package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateWordGroupContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.models.WordGroupModel
import javax.inject.Inject

class CreateWordGroupContractImpl @Inject constructor(
    private val imageRepository: ImageRepository,
    private val wordGroupRepository: WordGroupRepository,
) : CreateWordGroupContract {

    override suspend fun createWordGroup(
        groupName: String,
        primaryLanguage: Language,
        secondLanguage: Language,
        imageUrl: String?,
    ) {
        val imagePath = if (imageUrl == null) null else imageRepository.addImage(imageUrl)

        wordGroupRepository.insertWordGroup(
            WordGroupModel(
                0,
                groupName,
                primaryLanguage.toLanguageModel(),
                secondLanguage.toLanguageModel(),
                imagePath
            )
        )
    }

    private fun Language.toLanguageModel() : LanguageModel = LanguageModel(id,name)
}