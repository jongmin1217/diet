package com.bellminp.diet.ui.dialog.image_detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.lifecycle.LifecycleOwner
import com.bellminp.diet.R
import com.bellminp.diet.databinding.DialogImageDetailBinding
import com.bellminp.diet.databinding.DialogYnBinding
import com.bellminp.diet.ui.base.BaseDialog
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DialogData
import com.bellminp.diet.ui.dialog.yn.YnViewModel
import com.bellminp.diet.utils.BindAdapter

class ImageDetailDialog(
    private val mContext : Context,
    lifecycle : LifecycleOwner,
    private val viewModel: BaseViewModel,
    private val path : String
) : BaseDialog<DialogImageDetailBinding>(R.layout.dialog_image_detail,mContext, lifecycle){

    override fun initBinding() {
        binding.vm = viewModel
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BindAdapter.setImageDetail(binding.ivImage,path)


        binding.layout.setOnClickListener {
            dismiss()
            cancel()
        }

        binding.ivImage.setOnClickListener{}

    }



}