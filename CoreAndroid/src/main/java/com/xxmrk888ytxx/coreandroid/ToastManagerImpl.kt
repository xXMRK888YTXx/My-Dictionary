package com.xxmrk888ytxx.coreandroid

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.xxmrk888ytxx.androidcore.runOnUiThread
import com.xxmrk888ytxx.coreandroid.ShareInterfaces.ToastManager

class ToastManagerImpl(private val context: Context) : ToastManager {

    override fun showToast(text: String) = runOnUiThread {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("ResourceType")
    override fun showToast(resId: Int) {
        showToast(context.getString(resId))
    }
}