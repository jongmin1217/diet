package com.bellminp.diet.utils

import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.model.WorkOutData
import com.bellminp.diet.ui.adapter.CalendarAdapter
import com.bellminp.diet.ui.adapter.FoodImageAdapter
import com.bellminp.diet.ui.adapter.IssueListAdapter
import com.bellminp.diet.ui.adapter.WorkOutListAdapter
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

    @BindingAdapter("setImageUri")
    @JvmStatic
    fun setImageUri(imageView: ImageView, url: Any?) {
        url?.let {
            Glide.with(imageView)
                .load(it)
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

    fun foodListAdapter(recyclerview: RecyclerView,adapter : FoodImageAdapter) {
        if(recyclerview.adapter == null){
            if (!adapter.hasObservers()) adapter.setHasStableIds(true)

            recyclerview.layoutManager = GridLayoutManager(recyclerview.context, 2)
            recyclerview.adapter = adapter
            val animator = recyclerview.itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }
        }
    }

    @BindingAdapter("setFoodList")
    @JvmStatic
    fun setFoodList(recyclerview: RecyclerView, items: ArrayList<FoodData>?) {
        recyclerview.adapter?.let {
            if(it is FoodImageAdapter){
                it.items = items ?: ArrayList()
            }
        }
    }


    @BindingAdapter("setWorkOutList")
    @JvmStatic
    fun setWorkOutList(recyclerview: RecyclerView, items: ArrayList<WorkOutData>?) {
        recyclerview.adapter?.let {
            if(it is WorkOutListAdapter){
                it.items = items ?: ArrayList()
            }
        }
    }

    @BindingAdapter("workOutListAdapter")
    @JvmStatic
    fun workOutListAdapter(recyclerview: RecyclerView, adapter: WorkOutListAdapter) {
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

    @BindingAdapter("lastWeight","profile")
    @JvmStatic
    fun goalText(textView: TextView, lastWeight: Float?, profile : ProfileData?) {
        lastWeight?.let { weight ->
            profile?.let { profile ->
                textView.text = if(profile.type == 0){
                    if(profile.goalWeight >= weight) DietApplication.mInstance.resources.getString(R.string.goal_complete)
                    else String.format("목표까지 %.1fkg",weight - profile.goalWeight)
                }else{
                    if(profile.goalWeight <= weight) DietApplication.mInstance.resources.getString(R.string.goal_complete)
                    else String.format("목표까지 %.1fkg",profile.goalWeight - weight)
                }
            }
        }
    }

    @BindingAdapter("lastWeightImage","profileImage")
    @JvmStatic
    fun goalImage(imageView: ImageView, lastWeight: Float?, profile : ProfileData?) {
        lastWeight?.let { weight ->
            profile?.let { profile ->
                imageView.setBackgroundResource(if(profile.type == 0){
                    if(profile.goalWeight >= weight) R.drawable.img_rating
                    else R.drawable.imgcheerleader
                }else{
                    if(profile.goalWeight <= weight) R.drawable.img_rating
                    else R.drawable.imgcheerleader
                })
            }
        }
    }
}