package com.jasonchen.cathayhomework.response.attractions

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Target(
    val id: Int,
    val name: String
) : Parcelable