package com.jasonchen.cathayhomework.view.detail

import android.app.AlertDialog
import android.content.Intent
import android.text.Html
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.jasonchen.cathayhomework.R
import com.jasonchen.cathayhomework.databinding.FragmentAttractionsDetailBinding
import com.jasonchen.cathayhomework.network.api.AttractionsApi
import com.jasonchen.cathayhomework.repository.AttractionsRepository
import com.jasonchen.cathayhomework.response.attractions.Data
import com.jasonchen.cathayhomework.utility.AppUtils.setSafeOnClickListener
import com.jasonchen.cathayhomework.utility.LanguageHelper
import com.jasonchen.cathayhomework.view.BaseFragment
import com.jasonchen.cathayhomework.view.attractions.AttractionsViewModel
import com.jasonchen.cathayhomework.view.common.WebViewActivity

class AttractionsDetailFragment :
    BaseFragment<AttractionsDetailViewModel, FragmentAttractionsDetailBinding, AttractionsRepository>() {
    private val parentViewModel by activityViewModels<AttractionsDetailViewModel> { factory }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding = FragmentAttractionsDetailBinding.inflate(inflater, container, false)

    override fun getViewModel(): Class<AttractionsDetailViewModel> =
        AttractionsDetailViewModel::class.java

    override fun getFragmentRepository(): AttractionsRepository =
        AttractionsRepository(remoteDataSource.buildApi(AttractionsApi::class.java))

    override fun initView() {
        subscribeDetailData()
    }

    private fun initViewPager(detailData: Data) {
        val adapter = AttractionsDetailPicAdapter(detailData.images)
        binding.viewpagerPic.adapter = adapter
    }

    private fun subscribeDetailData() {
        parentViewModel.detailData.observe(viewLifecycleOwner) { data ->
            initViewPager(data)
            binding.detailData = data
            binding.fab.setSafeOnClickListener {
                jumpToWebView(data)
            }
        }
    }

    private fun jumpToWebView(data: Data) {
        val intent = Intent(requireActivity(), WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.INTENT_WEB_URL, data.url)
        intent.putExtra(WebViewActivity.INTENT_WEB_TITLE, data.name)
        startActivity(intent)
    }
}