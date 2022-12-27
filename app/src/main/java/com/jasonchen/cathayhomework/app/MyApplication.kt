package com.jasonchen.cathayhomework.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.jasonchen.cathayhomework.di.component.AppComponent
import com.jasonchen.cathayhomework.di.component.DaggerAppComponent
import com.jasonchen.cathayhomework.di.module.ApplicationModule
import com.jasonchen.cathayhomework.di.module.NetWorkModule

class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        var instance: MyApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        createComponent()
    }

    private fun createComponent() {
        component = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(this))
            .netWorkModule(NetWorkModule()).build()
    }
}