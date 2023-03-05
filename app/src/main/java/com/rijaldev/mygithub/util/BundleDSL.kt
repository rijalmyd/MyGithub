package com.rijaldev.mygithub.util

import android.os.Bundle

object BundleDSL {

    fun putExtra(action: Bundle.() -> Unit): Bundle {
        val bundle = Bundle()
        bundle.action()
        return bundle
    }
}