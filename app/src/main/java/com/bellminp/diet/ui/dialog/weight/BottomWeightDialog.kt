package com.bellminp.diet.ui.dialog.weight

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomMonthBinding
import com.bellminp.diet.databinding.LayoutBottomWeightBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.data.DateData

class BottomWeightDialog(
    private val initValue: Float?,
    private val id : Long?,
    private val dateData : DateData
) : BaseBottomSheetDialog<LayoutBottomWeightBinding, BottomWeightViewModel>(R.layout.layout_bottom_weight) {

    override val viewModel by activityViewModels<BottomWeightViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(initValue != null){
            viewModel.weight.value = initValue.toString()
            binding.tvTitle.text = DietApplication.mInstance.resources.getString(R.string.edit_weight)
        }else{
            binding.tvTitle.text = DietApplication.mInstance.resources.getString(R.string.post_weight)
        }

        binding.editWeight.run {
            binding.editWeight.isFocusableInTouchMode = true
            binding.editWeight.requestFocus()
            postDelayed({
                binding.editWeight.setSelection(binding.editWeight.text!!.length)
            }, 100)
        }

        initListener()
    }

    private fun initListener(){
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            viewModel.addWeight(id,dateData)
        }
    }

}