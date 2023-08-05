package com.xxmrk888ytxx.archivercreator

import net.lingala.zip4j.ZipFile
import java.io.File

internal class ArchiverCreatorImpl : ArchiverCreator {

    override fun createArchiver(file: File): ArchiverFile {
        val zipFile = ZipFile(file)

        return ArchiverFileImpl(file,zipFile)
    }
}