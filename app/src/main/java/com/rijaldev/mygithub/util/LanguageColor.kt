package com.rijaldev.mygithub.util

import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.rijaldev.mygithub.R

object LanguageColor {
    fun TextView.setLeftDrawableColor(context: Context, language: String?) {
        val colorFilter =
            when (language) {
                "Python" -> LightingColorFilter(Color.LTGRAY, Color.GREEN)
                "CSS", "C++", "Java", "Ruby" -> LightingColorFilter(Color.LTGRAY, Color.RED)
                "C", "PHP", "Kotlin" -> LightingColorFilter(Color.LTGRAY, Color.BLUE)
                "Erlang" -> LightingColorFilter(Color.LTGRAY, Color.CYAN)
                "HTML", "Swift" -> LightingColorFilter(Color.LTGRAY, Color.CYAN)
                "Shell" -> LightingColorFilter(Color.LTGRAY, Color.BLACK)
                "Dart", "Go", "R" -> LightingColorFilter(Color.LTGRAY, Color.CYAN)
                "TypeScript" -> LightingColorFilter(Color.LTGRAY, Color.MAGENTA)
                "JavaScript" -> LightingColorFilter(Color.LTGRAY, Color.YELLOW)
                else -> LightingColorFilter(Color.DKGRAY, Color.DKGRAY)
            }

        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_dot)
        drawable?.colorFilter = colorFilter

        setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
    }
}