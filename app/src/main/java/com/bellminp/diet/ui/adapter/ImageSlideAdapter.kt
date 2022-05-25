package com.bellminp.diet.ui.adapter

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.bellminp.diet.databinding.LayoutImageSlideBinding
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.ui.base.BasePagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

class ImageSlideAdapter(
    private val context: Context,
    private val lifecycle: LifecycleOwner,
    private val list: ArrayList<FoodData>,
    private val onClick : () -> Unit
) :BasePagerAdapter(list){
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val binding =
            LayoutImageSlideBinding.inflate(LayoutInflater.from(context), container, false)
        binding.lifecycleOwner = lifecycle

        binding.model = list[position]


        container.addView(binding.root)

        binding.ivImage.setOnClickListener {
            onClick()
        }

        return binding.root

    }

}