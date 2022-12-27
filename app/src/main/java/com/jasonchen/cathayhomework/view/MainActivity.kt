package com.jasonchen.cathayhomework.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.View.OnClickListener
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.jasonchen.cathayhomework.R
import com.jasonchen.cathayhomework.databinding.ActivityMainBinding
import com.jasonchen.cathayhomework.network.api.AttractionsApi
import com.jasonchen.cathayhomework.repository.AttractionsRepository
import com.jasonchen.cathayhomework.utility.LanguageHelper
import com.jasonchen.cathayhomework.view.attractions.AttractionsViewModel

class MainActivity :
    BaseActivity<AttractionsViewModel, ActivityMainBinding, AttractionsRepository>() {
    override fun getActivityBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getActivityRepository(): AttractionsRepository {
        return AttractionsRepository(remoteDataSource.buildApi(AttractionsApi::class.java))
    }

    override fun getViewModel(): Class<AttractionsViewModel> {
        return AttractionsViewModel::class.java
    }

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.activity_main_toolbar_title)
    }
}