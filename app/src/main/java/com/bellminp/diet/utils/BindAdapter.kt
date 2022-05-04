package com.bellminp.diet.utils

import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bellminp.diet.R
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.ui.adapter.IssueListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target

object BindAdapter {

    @BindingAdapter("setImage")
    @JvmStatic
    fun setImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView)
                .load(it)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL)
                .into(imageView)
        }
    }

    @BindingAdapter("setImageDetail")
    @JvmStatic
    fun setImageDetail(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView)
                .load(it)
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

    @BindingAdapter("setIssueList")
    @JvmStatic
    fun setIssueList(recyclerview: RecyclerView, items: ArrayList<DailyData>?) {
        recyclerview.adapter?.let {
            if(it is IssueListAdapter){
                it.items = items ?: ArrayList()
            }
        }
    }

    @BindingAdapter("issueListAdapter")
    @JvmStatic
    fun issueListAdapter(recyclerview: RecyclerView, adapter: IssueListAdapter) {
        if (recyclerview.adapter == null) {
            if (!adapter.hasObservers()) adapter.setHasStableIds(true)

            val lm = LinearLayoutManager(recyclerview.context)

            recyclerview.layoutManager = lm
            recyclerview.adapter = adapter

            val animator = recyclerview.itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }
    }


}