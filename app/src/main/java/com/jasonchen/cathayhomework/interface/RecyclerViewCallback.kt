package com.jasonchen.cathayhomework.`interface`

interface RecyclerViewCallback<T> {
    fun onItemSelect(position: Int, data: T)
    fun preLoadNextPage()
}