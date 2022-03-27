package com.bellminp.diet.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bellminp.diet.R
import com.bellminp.diet.common.listener.BottomBasicListener
import com.bellminp.diet.databinding.LayoutBottomBasicBinding
import com.bellminp.diet.databinding.LayoutBottomDateBinding
import com.bellminp.diet.ui.adapter.BottomSheetAdapter
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.data.BottomSheetData
import com.bellminp.diet.utils.Utils
import com.shawnlin.numberpicker.NumberPicker

class BottomDateDialog(private val initValue : String?) : BaseBottomSheetDialog<LayoutBottomDateBinding, BottomDateViewModel>(R.layout.layout_bottom_date){

    override val viewModel by activityViewModels<BottomDateViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initValue()
    }

    private fun initValue(){
        val monthList = arrayOf("January","February","March","April","May","June","July","August","Srptember","October","November","December")

        binding.npYear.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npDay.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.npYear.maxValue = Utils.getYear()
        binding.npYear.minValue = 1950
        binding.npYear.wrapSelectorWheel = false

        binding.npMonth.minValue = 1
        binding.npMonth.wrapSelectorWheel = true
        binding.npMonth.displayedValues = monthList


        binding.npDay.minValue = 1
        binding.npDay.wrapSelectorWheel = true

        binding.npYear.setOnValueChangedListener { _, _, _ ->
            settingDate()
        }

        binding.npMonth.setOnValueChangedListener { _, _, _ ->
            settingDate()
        }


        if(initValue == null){
            binding.npYear.value = Utils.getYear()

            settingDate()

            binding.npMonth.value = Utils.getMonth()
            binding.npDay.value = Utils.getDay()

            if(binding.npYear.value == Utils.getYear() && binding.npMonth.value == Utils.getMonth()) binding.npDay.maxValue = Utils.getDay()
        }else{
            binding.npYear.value = initValue.split(".")[0].toInt()

            settingDate()

            binding.npMonth.value = initValue.split(".")[1].toInt()
            binding.npDay.value = initValue.split(".")[2].toInt()

            if(binding.npYear.value == Utils.getYear() && binding.npMonth.value == Utils.getMonth()) binding.npDay.maxValue = Utils.getDay()
        }
    }

    private fun settingDate(){
        if(binding.npYear.value == Utils.getYear()) binding.npMonth.maxValue = Utils.getMonth()
        else binding.npMonth.maxValue = 12

        when(binding.npMonth.value){
            1,3,5,7,8,10,12 -> binding.npDay.maxValue = 31
            4,6,9,11 -> binding.npDay.maxValue = 30
            2 -> binding.npDay.maxValue = if(binding.npYear.value%4 == 0) 29 else 28
        }
    }


    private fun initListener(){
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            dismiss()
            viewModel.okClick(
                binding.npYear.value,
                binding.npMonth.value,
                binding.npDay.value
            )
        }
    }

}