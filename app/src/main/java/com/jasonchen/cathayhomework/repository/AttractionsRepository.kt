package com.jasonchen.cathayhomework.repository

import com.jasonchen.cathayhomework.app.MyApplication
import com.jasonchen.cathayhomework.network.api.AttractionsApi
import com.jasonchen.cathayhomework.utility.LanguageHelper
import javax.inject.Inject

class AttractionsRepository @Inject constructor(private val api: AttractionsApi): BaseRepository() {
    suspend fun queryAttractions(params: Map<String,String>) = safeApiCall {
        api.queryAttractions(LanguageHelper.lang, params)
    }
}