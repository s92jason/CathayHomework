package com.jasonchen.cathayhomework.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jasonchen.cathayhomework.response.attractions.Data

class AttractionsDetailViewModel: ViewModel() {
    private val _detailData = MutableLiveData<Data>()
    val detailData: LiveData<Data> = _detailData

    fun setDetailData(detailData: Data) {
        _detailData.value = detailData
    }
}