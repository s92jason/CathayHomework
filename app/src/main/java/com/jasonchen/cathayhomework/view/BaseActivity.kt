package com.jasonchen.cathayhomework.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jasonchen.cathayhomework.network.RemoteDataSource
import com.jasonchen.cathayhomework.repository.BaseRepository
import com.jasonchen.cathayhomework.viewModel.ViewModelFactory

abstract class BaseActivity<VM : ViewModel, B : ViewDataBinding, R : BaseRepository> :
    AppCompatActivity() {
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getActivityBinding()
        setContentView(binding.root)

        val factory = ViewModelFactory(getActivityRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        initView()
    }

    protected abstract fun getActivityBinding(): B

    protected abstract fun getActivityRepository(): R

    protected abstract fun getViewModel(): Class<VM>

    protected abstract fun initView()
}