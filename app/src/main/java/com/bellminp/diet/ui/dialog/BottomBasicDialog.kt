package com.bellminp.diet.ui.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bellminp.diet.R
import com.bellminp.diet.common.listener.BottomBasicListener
import com.bellminp.diet.databinding.LayoutBottomBasicBinding
import com.bellminp.diet.ui.adapter.BottomSheetAdapter
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BottomSheetData

class BottomBasicDialog(
    private val items : ArrayList<BottomSheetData>
) : BaseBottomSheetDialog<LayoutBottomBasicBinding, BottomBasicViewModel>(R.layout.layout_bottom_basic),
BottomBasicListener{

    override val viewModel by activityViewModels<BottomBasicViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initListener()
    }

    private fun initAdapter(){
        val adapter = BottomSheetAdapter(this)

        if(!adapter.hasObservers()) adapter.setHasStableIds(true)

        val lm = LinearLayoutManager(binding.recyclerviewBottom.context)

        binding.recyclerviewBottom.layoutManager = lm
        binding.recyclerviewBottom.adapter = adapter

        val animator = binding.recyclerviewBottom.itemAnimator
        if(animator is SimpleItemAnimator){
            animator.supportsChangeAnimations = false
        }

        adapter.items = items

    }

    private fun initListener(){
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun bottomSheetClick(bottomSheetData: BottomSheetData) {
        dismiss()
        viewModel.bottomSheetClick(bottomSheetData)
    }
}