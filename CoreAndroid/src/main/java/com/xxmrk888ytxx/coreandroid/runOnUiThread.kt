package com.xxmrk888ytxx.androidcore

import android.os.Handler
import android.os.Looper
import com.xxmrk888ytxx.coreandroid.AndroidLogger

private val handler by lazy {
    Handler(Looper.getMainLooper())
}

fun runOnUiThread(action:() -> Unit) : Unit {
    handler.post(action)
}