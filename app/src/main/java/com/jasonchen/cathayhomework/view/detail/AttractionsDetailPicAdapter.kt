package com.jasonchen.cathayhomework.view.detail

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jasonchen.cathayhomework.R
import com.jasonchen.cathayhomework.databinding.ViewAttrationsDetailPicItemBinding
import com.jasonchen.cathayhomework.response.attractions.Data
import com.jasonchen.cathayhomework.response.attractions.Image

class AttractionsDetailPicAdapter(private val images: List<Image>) :
    RecyclerView.Adapter<AttractionsDetailPicAdapter.SliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            ViewAttrationsDetailPicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val image = images[position]
        holder.bind(position, image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class SliderViewHolder(val binding: ViewAttrationsDetailPicItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, image: Image) {
            setImage(image)
        }

        private fun setImage(image: Image) {
            binding.progressBar.visibility = View.VISIBLE

            Glide.with(binding.root.context)
                .load(image.src)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .centerInside()
                .error(R.drawable.ic_baseline_image_24)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.imageViewPic)
        }

    }
}