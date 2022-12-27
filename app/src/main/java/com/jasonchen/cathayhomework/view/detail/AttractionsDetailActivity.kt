package com.jasonchen.cathayhomework.view.detail

import android.view.MenuItem
import androidx.navigation.navArgs
import com.jasonchen.cathayhomework.databinding.ActivityAttractionsDetailBinding
import com.jasonchen.cathayhomework.network.api.AttractionsApi
import com.jasonchen.cathayhomework.repository.AttractionsRepository
import com.jasonchen.cathayhomework.view.BaseActivity

class AttractionsDetailActivity :
    BaseActivity<AttractionsDetailViewModel, ActivityAttractionsDetailBinding, AttractionsRepository>() {
    private val args by navArgs<AttractionsDetailActivityArgs>()

    override fun getActivityBinding(): ActivityAttractionsDetailBinding =
        ActivityAttractionsDetailBinding.inflate(layoutInflater)

    override fun getActivityRepository(): AttractionsRepository =
        AttractionsRepository(remoteDataSource.buildApi(AttractionsApi::class.java))

    override fun getViewModel(): Class<AttractionsDetailViewModel> =
        AttractionsDetailViewModel::class.java

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        val detailData = args.detailData

        initToolBar(detailData.name)
        viewModel.setDetailData(detailData)
    }

    private fun initToolBar(title: String) {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)

            it.title = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}