package com.xxmrk888ytxx.mydictionary.domain.VersionProvider

import javax.inject.Inject
import com.xxmrk888ytxx.mydictionary.BuildConfig

class VersionProviderImpl @Inject constructor() : VersionProvider {


    override val versionName: String by lazy {
        BuildConfig.VERSION_NAME
    }
}