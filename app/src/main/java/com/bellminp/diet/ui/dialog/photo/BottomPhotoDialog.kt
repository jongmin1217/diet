package com.bellminp.diet.ui.dialog.photo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomPhotoBinding
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.dialog.weight.BottomWeightViewModel

class BottomPhotoDialog(
    private val selectGallery: (() -> Unit),
    private val selectCamera: (() -> Unit)
) : BaseBottomSheetDialog<LayoutBottomPhotoBinding,BottomPhotoViewModel>(R.layout.layout_bottom_photo) {
    override val viewModel by activityViewModels<BottomPhotoViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }


    private fun initListener(){
        binding.layoutGallery.setOnClickListener {
            dismiss()
            selectGallery()
        }

        binding.layoutCamera.setOnClickListener {
            dismiss()
            selectCamera()
        }
    }
}