package com.bellminp.diet.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bellminp.diet.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<B : ViewDataBinding,VM : BaseViewModel>(@LayoutRes val layoutId: Int) : BottomSheetDialogFragment() {

    abstract val viewModel : VM
    abstract fun initBinding()
    lateinit var binding : B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        initBinding()
        initViewModelObserve()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.NewDialog).apply {
            setCanceledOnTouchOutside(true)
            if(layoutId == R.layout.layout_bottom_add_content) behavior.isDraggable = false
        }
    }

    open fun initViewModelObserve(){
        with(viewModel){
            dialogDismiss.observe(viewLifecycleOwner,{
                dismiss()
            })
        }
    }

}