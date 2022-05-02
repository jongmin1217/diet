package com.bellminp.diet.ui.dialog.month

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomMonthBinding
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.utils.Utils
import com.shawnlin.numberpicker.NumberPicker

class BottomMonthDialog(private val initValue : String) : BaseBottomSheetDialog<LayoutBottomMonthBinding, BottomMonthViewModel>(
    R.layout.layout_bottom_month){

    override val viewModel by activityViewModels<BottomMonthViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initValue()
    }

    private fun initValue(){

        binding.npYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.npYear.maxValue = Utils.getYear()
        binding.npYear.minValue = 1950
        binding.npYear.wrapSelectorWheel = false

        binding.npMonth.minValue = 1
        binding.npMonth.wrapSelectorWheel = true

        binding.npYear.setOnValueChangedListener { _, _, _ ->
            settingDate()
        }

        binding.npMonth.setOnValueChangedListener { _, _, _ ->
            settingDate()
        }


        binding.npYear.value = initValue.split(".")[0].toInt()

        settingDate()

        binding.npMonth.value = initValue.split(".")[1].toInt()
    }

    private fun settingDate(){
        if(binding.npYear.value == Utils.getYear()) binding.npMonth.maxValue = Utils.getMonth()
        else binding.npMonth.maxValue = 12
    }


    private fun initListener(){
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            dismiss()
            viewModel.okClick(
                binding.npYear.value,
                binding.npMonth.value
            )
        }
    }

}