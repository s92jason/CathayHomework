package com.jasonchen.cathayhomework.di.component

import com.jasonchen.cathayhomework.di.module.*
import com.jasonchen.cathayhomework.network.RemoteDataSource
import com.jasonchen.cathayhomework.view.MainActivity
import com.jasonchen.cathayhomework.view.attractions.AttractionsListFragment
import com.jasonchen.cathayhomework.view.detail.AttractionsDetailActivity
import com.jasonchen.cathayhomework.view.detail.AttractionsDetailFragment

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetWorkModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)

    fun inject(activity: AttractionsDetailActivity)

    fun inject(fragment: AttractionsListFragment)

    fun inject(fragment: AttractionsDetailFragment)

    fun inject(fragment: RemoteDataSource)
}