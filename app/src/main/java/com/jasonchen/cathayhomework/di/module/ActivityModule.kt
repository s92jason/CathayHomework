package com.jasonchen.cathayhomework.di.module

import android.app.Activity
import android.content.Context
import com.jasonchen.cathayhomework.di.ActivityContext
import com.jasonchen.cathayhomework.di.PerActivity

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @PerActivity
    @Provides
    @ActivityContext
    fun provideContext(): Context = activity
}
