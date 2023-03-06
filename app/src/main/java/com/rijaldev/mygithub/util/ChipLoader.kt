package com.rijaldev.mygithub.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.rijaldev.mygithub.R

object ChipLoader {
    fun ChipGroup.addChip(context: Context, label: String) {
        Chip(context).apply {
            id = View.generateViewId()
            text = label
            isClickable = true
            isCheckedIconVisible = false
            isCheckable = false
            isFocusable = true
            setEnsureMinTouchTargetSize(false)
            chipBackgroundColor = ContextCompat.getColorStateList(context, R.color.white)
            chipStrokeWidth = 0.5f
            chipStrokeColor = ContextCompat.getColorStateList(context, R.color.gray)
            addView(this)
        }
    }
}