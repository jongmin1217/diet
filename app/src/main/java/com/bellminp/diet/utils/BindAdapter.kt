package com.bellminp.diet.utils

import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bellminp.diet.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object BindAdapter {

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView)
                .load(it)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
        }
    }

    @BindingAdapter("imageSelect")
    @JvmStatic
    fun imageSelect(imageView: ImageView, url: String?) {
        if(url == null){
            imageView.setImageResource(R.drawable.shape_circle_add_image)
        }else{
            Glide.with(imageView)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
        }
    }

    @BindingAdapter("btnStatus")
    @JvmStatic
    fun btnEnable(button: Button, value: Boolean) {
        button.isEnabled = value
        if (value) button.setBackgroundResource(R.drawable.shape_enable_button)
        else button.setBackgroundResource(R.drawable.shape_disable_button)
    }
}