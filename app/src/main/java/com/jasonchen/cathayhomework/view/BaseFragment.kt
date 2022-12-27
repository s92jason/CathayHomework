package com.jasonchen.cathayhomework.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.jasonchen.cathayhomework.network.RemoteDataSource
import com.jasonchen.cathayhomework.repository.BaseRepository
import com.jasonchen.cathayhomework.viewModel.ViewModelFactory

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding, R : BaseRepository> : Fragment() {
    protected lateinit var binding: B
    protected lateinit var viewModel: VM

    protected lateinit var factory: ViewModelFactory

    var remoteDataSource = RemoteDataSource()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )
            : View? {


        binding = getFragmentBinding(inflater, container) as B

        factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    protected abstract fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding

    protected abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentRepository(): R

    protected abstract fun initView()

    protected fun showErrorMsg(msg: String) {
        if (msg.isEmpty()) {
            return
        }
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show();
    }
}