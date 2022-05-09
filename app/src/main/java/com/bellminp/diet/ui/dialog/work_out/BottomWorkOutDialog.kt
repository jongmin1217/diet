package com.bellminp.diet.ui.dialog.work_out

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomWorkOutBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.WorkOutData
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.dialog.weight.BottomWeightViewModel
import com.bellminp.diet.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shawnlin.numberpicker.NumberPicker

class BottomWorkOutDialog(
    private val list: ArrayList<WorkOutData>?,
    private val position: Int?,
    private val id: Long?,
    private val dateData: DateData
) : BaseBottomSheetDialog<LayoutBottomWorkOutBinding, BottomWorkOutViewModel>(R.layout.layout_bottom_work_out) {
    override val viewModel by activityViewModels<BottomWorkOutViewModel>()

    private val hourList = DietApplication.mInstance.resources.getStringArray(R.array.hour)
    private val minList = DietApplication.mInstance.resources.getStringArray(R.array.min)

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.npStartHour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npStartMin.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npEndHour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.npEndMin.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        binding.npStartHour.minValue = 0
        binding.npStartHour.maxValue = 23
        binding.npStartHour.wrapSelectorWheel = false
        binding.npStartHour.displayedValues = hourList

        binding.npEndHour.minValue = 0
        binding.npEndHour.maxValue = 23
        binding.npEndHour.wrapSelectorWheel = false
        binding.npEndHour.displayedValues = hourList

        binding.npStartMin.minValue = 0
        binding.npStartMin.maxValue = 5
        binding.npStartMin.wrapSelectorWheel = true
        binding.npStartMin.displayedValues = minList

        binding.npEndMin.minValue = 0
        binding.npEndMin.maxValue = 5
        binding.npEndMin.wrapSelectorWheel = true
        binding.npEndMin.displayedValues = minList

        if (position == null) {
            viewModel.workOutType.value = String()
            binding.npStartHour.value = 12
            binding.npEndHour.value = 13
            binding.npStartMin.value = 0
            binding.npEndMin.value = 0
        } else {
            list?.let { list ->
                viewModel.workOutType.value = list[position].type
                val time = list[position].time
                val startHour = (time.split("|")[0]).split(":")[0]
                val startMin = (time.split("|")[0]).split(":")[1]
                val endHour = (time.split("|")[1]).split(":")[0]
                val endMin = (time.split("|")[1]).split(":")[1]

                binding.npStartHour.value = hourList.indexOfFirst { it == startHour }
                binding.npEndHour.value = hourList.indexOfFirst { it == endHour }
                binding.npStartMin.value = minList.indexOfFirst { it == startMin }
                binding.npEndMin.value = minList.indexOfFirst { it == endMin }
            }
        }

        binding.editWorkOut.run {
            binding.editWorkOut.isFocusableInTouchMode = true
            binding.editWorkOut.requestFocus()
            postDelayed({
                binding.editWorkOut.setSelection(binding.editWorkOut.text!!.length)
            }, 100)
        }

        initListener()
    }

    private fun dateCheck(type : Int){
        val startDate = String.format("%s%s",hourList[binding.npStartHour.value],minList[binding.npStartMin.value]).toInt()
        val endDate = String.format("%s%s",hourList[binding.npEndHour.value],minList[binding.npEndMin.value]).toInt()
        if(startDate >= endDate){
            if(type == 0) binding.npStartHour.value = binding.npEndHour.value - 1
            else binding.npEndHour.value = binding.npStartHour.value + 1
        }
    }


    private fun initListener() {
        binding.npStartHour.setOnValueChangedListener { _, _, _ ->
            dateCheck(0)
        }
        binding.npStartMin.setOnValueChangedListener { _, _, _ ->
            dateCheck(0)
        }
        binding.npEndHour.setOnValueChangedListener { _, _, _ ->
            dateCheck(1)
        }
        binding.npEndMin.setOnValueChangedListener { _, _, _ ->
            dateCheck(1)
        }


        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            String.format(
                "%s:%s|%s:%s",
                hourList[binding.npStartHour.value],
                minList[binding.npStartMin.value],
                hourList[binding.npEndHour.value],
                minList[binding.npEndMin.value]
            ).apply {
                viewModel.addWorkOut(id, dateData, list, position, this)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight()
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}