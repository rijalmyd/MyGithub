package com.rijaldev.mygithub.util

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.rijaldev.mygithub.R

object ColorType {
    fun TextView?.setColor(context: Context, type: String?) {
        when (type) {
            Constants.TYPE_USER -> this?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.blue_200))
                text = type
            }
            Constants.TYPE_ORGANIZATION -> this?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.red_200))
                text = type
            }
        }
    }
}