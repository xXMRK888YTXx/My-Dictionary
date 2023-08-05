package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.archivercreator.ArchiverCreator
import dagger.Module
import dagger.Provides

@Module
interface ArchiverCreatorModule {
    companion object {
        @Provides
        fun provideArchiverCreator() : ArchiverCreator {
            return ArchiverCreator.create()
        }
    }
}