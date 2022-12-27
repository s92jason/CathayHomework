package com.jasonchen.cathayhomework.network

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.jasonchen.cathayhomework.app.MyApplication
import com.jasonchen.cathayhomework.utility.AppConstants

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource {
    @Inject
    lateinit var okHttpClient : OkHttpClient

    fun <Api> buildApi(api: Class<Api>): Api
    {
        (MyApplication.instance as MyApplication).component
            .inject(this)

        return Retrofit.Builder()
            .baseUrl(AppConstants.URL.baseUrl)
            .client(
                okHttpClient
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

}