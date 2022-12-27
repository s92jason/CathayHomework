package com.jasonchen.cathayhomework.view.attractions

import android.app.AlertDialog
import android.view.*
import androidx.core.view.MenuProvider
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jasonchen.cathayhomework.R
import com.jasonchen.cathayhomework.`interface`.RecyclerViewCallback
import com.jasonchen.cathayhomework.databinding.FragmentAttractionsListBinding
import com.jasonchen.cathayhomework.network.api.AttractionsApi
import com.jasonchen.cathayhomework.repository.AttractionsRepository
import com.jasonchen.cathayhomework.response.attractions.Data
import com.jasonchen.cathayhomework.utility.AppConstants
import com.jasonchen.cathayhomework.utility.LanguageHelper
import com.jasonchen.cathayhomework.view.BaseFragment

class AttractionsListFragment :
    BaseFragment<AttractionsViewModel, FragmentAttractionsListBinding, AttractionsRepository>() {
    companion object {
        private const val DEF_LAYOUT_GRID_TYPE_COLUMN = 2
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding {
        return FragmentAttractionsListBinding.inflate(inflater, container, false)
    }

    override fun getFragmentRepository(): AttractionsRepository {
        return AttractionsRepository(remoteDataSource.buildApi(AttractionsApi::class.java))
    }

    override fun getViewModel(): Class<AttractionsViewModel> = AttractionsViewModel::class.java

    override fun initView() {
        initMenu(requireActivity())
        initRecyclerView(AppConstants.ListShowType.LIST)

        subscribesQueryAttractionsResult()

        viewModel.queryAttractions(false)
    }

    private fun subscribesQueryAttractionsResult() {
        viewModel.attractionsViewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is AttractionsViewModel.Fail -> {
                    binding.progressBar.visibility = View.GONE

                    viewState.errorCode
                    val errorCode = if (viewState.errorCode != null) "(${viewState.errorCode})" else ""
                    showErrorMsg("$errorCode ${viewState.errorBody?.string()}")
                }
                AttractionsViewModel.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is AttractionsViewModel.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val adapter = binding.recyclerViewAttractions.adapter as AttractionsListAdapter
                    adapter.submitList(viewState.dataList)
                }
            }
        }
    }

    private fun initRecyclerView(type: AppConstants.ListShowType) {
        val layoutManager = getLayoutManager(type)
        binding.recyclerViewAttractions.layoutManager = layoutManager
        binding.recyclerViewAttractions.adapter = getAdapter(type)
    }

    private fun initMenu(menuHost: FragmentActivity) {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_refresh -> {
                        viewModel.queryAttractions(true)
                        true
                    }
                    R.id.action_languange -> {
                        showLangSelectDialog()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun getLayoutManager(type: AppConstants.ListShowType): RecyclerView.LayoutManager {
        return when (type) {
            AppConstants.ListShowType.LIST -> {
                LinearLayoutManager(context)
            }
            else -> {
                GridLayoutManager(context, DEF_LAYOUT_GRID_TYPE_COLUMN)
            }
        }
    }

    private fun getAdapter(type: AppConstants.ListShowType): AttractionsListAdapter {
        return AttractionsListAdapter(type, object : RecyclerViewCallback<Data> {
            override fun onItemSelect(position: Int, data: Data) {
                val action =
                    AttractionsListFragmentDirections.actionAttractionsListFragmentToAttractionsDetailActivity(
                        data
                    )
                findNavController().navigate(action)
            }

            override fun preLoadNextPage() {
                viewModel.queryAttractions(false)
            }
        })
    }

    private fun showLangSelectDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_lang_selector_title)
            .setItems(LanguageHelper.langNameList.toTypedArray()) { dialog, which ->
                LanguageHelper.lang = AppConstants.Language.getLang(which)
                viewModel.queryAttractions(true)
            }
            .show()
    }
}