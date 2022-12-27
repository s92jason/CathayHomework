package com.jasonchen.cathayhomework.view.attractions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jasonchen.cathayhomework.R
import com.jasonchen.cathayhomework.`interface`.RecyclerViewCallback
import com.jasonchen.cathayhomework.databinding.ViewAttravtionsGridItemBinding
import com.jasonchen.cathayhomework.databinding.ViewAttravtionsListItemBinding
import com.jasonchen.cathayhomework.response.attractions.Data
import com.jasonchen.cathayhomework.utility.AppConstants
import com.jasonchen.cathayhomework.utility.AppConstants.ListShowType.*
import com.jasonchen.cathayhomework.utility.AppUtils.setSafeOnClickListener
import java.lang.Integer.max

class AttractionsListAdapter(
    private val showType: AppConstants.ListShowType,
    private val callback: RecyclerViewCallback<Data>
) : ListAdapter<Data, RecyclerView.ViewHolder>(DiffCallback()) {
    companion object {
        private const val DEF_PRELOAD_COUNT = 5
    }

    private var scrollState = RecyclerView.SCROLL_STATE_IDLE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LIST.index -> {
                ListViewHolder(
                    ViewAttravtionsListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                GridViewHolder(
                    ViewAttravtionsGridItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        checkLoadNextPage(position)

        val data = getItem(position)
        if (holder is ListViewHolder) {
            holder.bind(position, data)
        }
        else if (holder is GridViewHolder) {
            holder.bind(position, data)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (showType) {
            GRID -> GRID.index
            LIST -> LIST.index
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                scrollState = newState
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun checkLoadNextPage(position: Int) {
        if (scrollState != RecyclerView.SCROLL_STATE_IDLE &&
                position == max(itemCount - 1 - DEF_PRELOAD_COUNT, 0)) {
            callback.preLoadNextPage()
        }
    }

    inner class ListViewHolder(private val binding: ViewAttravtionsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, data: Data) {
            binding.root.setSafeOnClickListener {
                callback.onItemSelect(position, data)
            }

            setImage(data)
            setText(data)
        }

        private fun setImage(data: Data) {
            val images = data.images
            if (images.isEmpty()) {
                binding.imageviewPic.setImageResource(R.drawable.ic_baseline_image_24)
                return
            }

            Glide.with(binding.root.context)
                .load(images[0].src)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .centerInside()
                .error(R.drawable.ic_baseline_image_24)
                .into(binding.imageviewPic)
        }

        private fun setText(data: Data) {
            binding.textviewName.text = data.name
            binding.textviewIntro.text = data.introduction
        }
    }

    inner class GridViewHolder(private val binding: ViewAttravtionsGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, data: Data) {
            binding.root.setSafeOnClickListener {
                callback.onItemSelect(position, data)
            }

//            setImage(hit)
        }

//        private fun setImage(hit: Hit) {
//            Glide.with(binding.root.context)
//                .load(hit.previewURL)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(false)
//                .centerInside()
//                .error(R.drawable.ic_baseline_image_24)
//                .into(binding.imageviewPic)
//        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}