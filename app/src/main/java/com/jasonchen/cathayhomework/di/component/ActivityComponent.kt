package com.jasonchen.cathayhomework.di.component

import com.jasonchen.cathayhomework.di.PerActivity
import com.jasonchen.cathayhomework.di.module.ActivityModule
import dagger.Component

@PerActivity
@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent
