package com.xxmrk888ytxx.archivercreator

import java.io.File

interface ArchiverCreator {

    fun createArchiver(file: File) : ArchiverFile

    companion object {
        fun create() : ArchiverCreator {
            return ArchiverCreatorImpl()
        }
    }

}