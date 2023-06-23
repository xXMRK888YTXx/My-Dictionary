package com.xxmrk888ytxx.createwordgroupscreen.contract

import com.xxmrk888ytxx.createwordgroupscreen.models.Language

interface CreateWorkGroupContract {

    suspend fun createWordGroup(
        groupName: String,
        primaryLanguage: Language,
        secondLanguage: Language,
        imageUrl: String?,
    )
}