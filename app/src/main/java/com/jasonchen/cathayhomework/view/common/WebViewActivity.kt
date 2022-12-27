package com.jasonchen.cathayhomework.view.common

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil.bind
import androidx.databinding.DataBindingUtil.setContentView
import com.jasonchen.cathayhomework.R
import com.jasonchen.cathayhomework.databinding.ActivityWebViewBinding
import com.jasonchen.cathayhomework.repository.BaseRepository
import com.jasonchen.cathayhomework.view.BaseActivity
import com.jasonchen.cathayhomework.viewModel.BaseViewModel

class WebViewActivity : AppCompatActivity() {
    companion object {
        const val INTENT_WEB_TITLE = "webTitle"
        const val INTENT_WEB_URL = "webUrl"
    }

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initToolBar(getIntentTitle())

        initView()
        val url = getIntentUrl()
        loadUrl(url)
    }

    private fun initToolBar(title: String) {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)

            it.title = title
        }
    }

    private fun initView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.webViewClient = WebViewClient()
    }

    private fun getIntentUrl(): String {
        return intent.getStringExtra(INTENT_WEB_URL) ?: ""
    }

    private fun getIntentTitle(): String {
        return intent.getStringExtra(INTENT_WEB_TITLE) ?: getString(R.string.app_name)
    }

    private fun loadUrl(url: String) {
        binding.webView.loadUrl(url)
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