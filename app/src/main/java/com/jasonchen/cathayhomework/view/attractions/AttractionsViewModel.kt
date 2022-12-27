package com.jasonchen.cathayhomework.view.attractions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jasonchen.cathayhomework.network.Resource
import com.jasonchen.cathayhomework.repository.AttractionsRepository
import com.jasonchen.cathayhomework.response.attractions.Data
import com.jasonchen.cathayhomework.response.attractions.request.AttractionsReq
import com.jasonchen.cathayhomework.utility.AppConstants.URL.Companion.DEF_ATTRACTIONS_PAGE_INIT
import com.jasonchen.cathayhomework.viewModel.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

class AttractionsViewModel @Inject constructor(private val repository: AttractionsRepository) :
    BaseViewModel() {

    private val _attractionsViewState: MutableLiveData<AttractionsViewState> =
        MutableLiveData()
    val attractionsViewState: LiveData<AttractionsViewState> = _attractionsViewState

    private var attractionsTotal = 0
    private var attractionReqPage = DEF_ATTRACTIONS_PAGE_INIT
    var attractions: MutableList<Data> = mutableListOf()
        private set

    fun queryAttractions(isRefresh: Boolean) {
        if (isRefresh) {
            refresh()
        }

        if (attractionsTotal - 1 == attractionReqPage) {
            return
        }

        val params = packageQueryAttractionsParams(attractionReqPage)

        _attractionsViewState.value = Loading
        sendAttractionsApi(params)
    }

    private fun refresh() {
        attractionReqPage = DEF_ATTRACTIONS_PAGE_INIT
        attractions = mutableListOf()

        _attractionsViewState.value = Success(listOf())
    }

    private fun packageQueryAttractionsParams(page: Int = 0): Map<String, String> {
        val req = AttractionsReq(page = page)
        return req.getParams()
    }

    private fun sendAttractionsApi(params: Map<String, String>) = viewModelScope.launch {
        attractionReqPage++

        when (val resource = repository.queryAttractions(params)) {
            is Resource.Failure -> {
                _attractionsViewState.value =
                    Fail(resource.isNetworkError, resource.errorCode, resource.errorBody)
            }
            is Resource.Success -> {
                attractionsTotal = resource.value.total
                attractions = updateAttractions(attractions, resource.value.data)
                _attractionsViewState.value = Success(attractions)
            }
        }
    }

    private fun updateAttractions(
        oldAttractions: List<Data>,
        newAttractions: List<Data>
    ): MutableList<Data> {
        val result = mutableListOf<Data>()
        result.addAll(oldAttractions)
        result.addAll(newAttractions)
        return result
    }

    sealed class AttractionsViewState
    data class Success(val dataList: List<Data>) : AttractionsViewState()
    data class Fail(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : AttractionsViewState()

    object Loading : AttractionsViewState()
}