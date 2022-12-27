package com.jasonchen.cathayhomework.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jasonchen.cathayhomework.repository.AttractionsRepository
import com.jasonchen.cathayhomework.repository.BaseRepository
import com.jasonchen.cathayhomework.view.attractions.AttractionsViewModel
import com.jasonchen.cathayhomework.view.detail.AttractionsDetailViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {

            modelClass.isAssignableFrom(AttractionsViewModel::class.java) -> AttractionsViewModel(
                repository as AttractionsRepository
            ) as T

            modelClass.isAssignableFrom(AttractionsDetailViewModel::class.java) -> AttractionsDetailViewModel() as T

            else -> throw IllegalArgumentException("ViewModel Not Found")
        }

    }


}