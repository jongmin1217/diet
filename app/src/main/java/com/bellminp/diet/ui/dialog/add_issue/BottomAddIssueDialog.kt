package com.bellminp.diet.ui.dialog.add_issue

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomAddIssueBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.dialog.add_content.BottomAddContentViewModel
import com.bellminp.diet.utils.Constants

class BottomAddIssueDialog(
    private val type : Int,
    private val list : ArrayList<DailyData>?,
    private val position : Int?,
    private val id : Long?,
    private val dateData : DateData
) : BaseBottomSheetDialog<LayoutBottomAddIssueBinding,BottomAddIssueViewModel>(R.layout.layout_bottom_add_issue) {

    override val viewModel by activityViewModels<BottomAddIssueViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = if(type == Constants.GOOD_LIST){
            if(position == null) DietApplication.mInstance.resources.getString(R.string.add_good_list)
            else DietApplication.mInstance.resources.getString(R.string.edit_good_list)
        }else{
            if(position == null) DietApplication.mInstance.resources.getString(R.string.add_bad_list)
            else DietApplication.mInstance.resources.getString(R.string.edit_bad_list)
        }

        viewModel.issueText.value =if(position == null) ""
        else{
            if(list == null) ""
            else list[position].content
        }

        binding.editIssue.run {
            binding.editIssue.isFocusableInTouchMode = true
            binding.editIssue.requestFocus()
            postDelayed({
                binding.editIssue.setSelection(binding.editIssue.text!!.length)
            }, 100)
        }

        initListener()
    }

    fun initListener(){
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            viewModel.addIssue(id,dateData,type, list, position)
        }
    }
}