package com.xxmrk888ytxx.archivercreator

import net.lingala.zip4j.ZipFile
import java.io.Closeable
import java.io.File

abstract class ArchiverFile(
    val fileLocation:File,
    protected val zipFile: ZipFile
) : Closeable {

    abstract suspend fun addFile(file: File)

    abstract suspend fun extractTo(file: File)

    abstract val innerFilesName:List<String>


}
