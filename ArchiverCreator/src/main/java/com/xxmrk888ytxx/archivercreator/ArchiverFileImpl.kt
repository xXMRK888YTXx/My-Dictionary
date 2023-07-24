package com.xxmrk888ytxx.archivercreator

import net.lingala.zip4j.ZipFile
import java.io.File

internal class ArchiverFileImpl(
    fileLocation: File,
    zipFile: ZipFile
) : ArchiverFile(
    fileLocation,
    zipFile
) {
    override suspend fun addFile(file: File) {
        if(file.isDirectory) {
            zipFile.addFolder(file)
        } else {
            zipFile.addFile(file)
        }
    }

    override suspend fun extractTo(file: File) {
        zipFile.extractAll(file.absolutePath)
    }

    override fun close() {
        zipFile.close()
    }
}