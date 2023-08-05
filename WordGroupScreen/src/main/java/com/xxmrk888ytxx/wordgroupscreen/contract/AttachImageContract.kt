package com.xxmrk888ytxx.wordgroupscreen.contract

import android.net.Uri

interface AttachImageContract {

    suspend fun attachImage(wordGroupId:Int,imageUri: Uri)
}