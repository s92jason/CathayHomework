package com.jasonchen.cathayhomework.network.api

import com.jasonchen.cathayhomework.response.attractions.AttractionsRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface AttractionsApi {
    @Headers("Accept:application/json")
    @GET("{lang}/Attractions/All")
    suspend fun queryAttractions(
        @Path("lang") lang: String,
        @QueryMap params: Map<String, String>
    ): AttractionsRes
}