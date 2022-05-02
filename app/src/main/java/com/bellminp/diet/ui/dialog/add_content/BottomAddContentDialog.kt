package com.bellminp.diet.ui.dialog.add_content

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomAddContentBinding
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.dialog.weight.BottomWeightViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomAddContentDialog(
    private val initValue: String?,
    private val id : Long?,
    private val dateData : DateData
)  :
    BaseBottomSheetDialog<LayoutBottomAddContentBinding, BottomAddContentViewModel>(R.layout.layout_bottom_add_content) {

    override val viewModel by activityViewModels<BottomAddContentViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValue?.let {
            viewModel.content.value = it
        }

        binding.editContent.run {
            binding.editContent.isFocusableInTouchMode = true
            binding.editContent.requestFocus()
            postDelayed({
                binding.editContent.setSelection(binding.editContent.text!!.length)
            }, 100)
        }

        initListener()
    }

    fun initListener(){
        binding.btnSave.setOnClickListener {
            viewModel.addContent(id,dateData)
        }
    }


}