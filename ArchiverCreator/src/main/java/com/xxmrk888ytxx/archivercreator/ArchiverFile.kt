package com.xxmrk888ytxx.archivercreator

import net.lingala.zip4j.ZipFile
import java.io.Closeable
import java.io.File

/**
 * [Ru]
 *  Абстракция для управления файлом архива
 */

/**
 * [En]
 * Abstraction for archive file management
 */
abstract class ArchiverFile(
    val fileLocation:File,
    protected val zipFile: ZipFile
) : Closeable {

    /**
     * [Ru]
     *  Метод для добавления файла в архив
     */

    /**
     * [En]
     * Method for adding file to archiver
     */
    abstract suspend fun addFile(file: File)

    /**
     * [Ru]
     * Распаковывает файлы из архива в переданную директорию
     */

    /**
     * [En]
     * Extract files from archiver to the transferred directory
     */
    abstract suspend fun extractTo(file: File)

    /**
     * [Ru]
     *  Возвращает названия файлов в архиве
     */

    /**
     * [En]
     * Returns files in the archiver
     */

    abstract val innerFilesName:List<String>


}
