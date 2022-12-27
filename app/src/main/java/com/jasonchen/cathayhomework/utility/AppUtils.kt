package com.jasonchen.cathayhomework.utility

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View

object AppUtils {
    private const val DOUBLE_CLICK_INTERVAL: Long = 500
    private var m_lLastClickTime: Long = 0
    val isDoubleClick: Boolean
        get() {
            val lCurr = System.currentTimeMillis()
            val bRet = lCurr - m_lLastClickTime <= DOUBLE_CLICK_INTERVAL
            if (!bRet) {
                m_lLastClickTime = lCurr
            }
            return bRet
        }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeOnClickListener = SafeOnClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeOnClickListener)
    }

    inline fun <reified T : Parcelable> Bundle.getParcelable(name: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(
            name,
            T::class.java
        )
        else -> getParcelable(name) as? T
    }

    inline fun <reified T : Parcelable> Intent.getParcelable(name: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(
            name,
            T::class.java
        )
        else -> getParcelableExtra(name) as? T
    }
}

class SafeOnClickListener(private val onSafeClick: (View) -> Unit) : View.OnClickListener {
    override fun onClick(v: View) {
        if (AppUtils.isDoubleClick) {
            return
        }
        onSafeClick(v)
    }
}