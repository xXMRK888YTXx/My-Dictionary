package com.xxmrk888ytxx.mydictionary.glue.CreateWordGroupScreen

import com.xxmrk888ytxx.createwordgroupscreen.contract.CreateWorkGroupContract
import com.xxmrk888ytxx.createwordgroupscreen.models.Language
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.ImageRepository.ImageRepository
import com.xxmrk888ytxx.mydictionary.data.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.models.LanguageModel
import com.xxmrk888ytxx.mydictionary.models.WordGroupModel
import javax.inject.Inject

class CreateWorkGroupContractImpl @Inject constructor(
    private val imageRepository: ImageRepository,
    private val wordGroupRepository: WordGroupRepository,
) : CreateWorkGroupContract {

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
                primaryLanguage.toLanguageModel(),
                secondLanguage.toLanguageModel(),
                imagePath
            )
        )
    }

    private fun Language.toLanguageModel() : LanguageModel = LanguageModel(id,name)
}