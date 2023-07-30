package com.xxmrk888ytxx.archivercreator

import java.io.File


/**
 * [Ru]
 *
 * Интерфейс для создания архивов
 */

/**
 * [En]
 * Interface for creating archivers
 */
interface ArchiverCreator {

    fun createArchiver(file: File) : ArchiverFile

    companion object {
        fun create() : ArchiverCreator {
            return ArchiverCreatorImpl()
        }
    }

}