package com.rijaldev.mygithub.util

import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

object NumberFormatter {

    fun Int?.shortenNumber(): String {
        val numberBefore = this?.toDouble() ?: 0.0
        val suffixChar = "KMGTPE"
        val formatter = DecimalFormat("###.#")

        return if (numberBefore < 1000.0) {
            formatter.format(numberBefore)
        } else {
            val exp = (ln(numberBefore) / ln(1000.0)).toInt()
            val newFormat = formatter.format(
                numberBefore / 1000.0.pow(exp.toDouble())
            )  + suffixChar[exp - 1]
            newFormat.replace(",", ".")
        }
    }
}