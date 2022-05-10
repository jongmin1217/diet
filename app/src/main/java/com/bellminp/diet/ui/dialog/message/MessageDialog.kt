package com.bellminp.diet.ui.dialog.message

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.bellminp.diet.R
import com.bellminp.diet.databinding.DialogMessageBinding
import com.bellminp.diet.ui.base.BaseDialog
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.dialog.yn.YnViewModel

class MessageDialog(
    mContext : Context,
    lifecycle : LifecycleOwner,
    private val viewModel: BaseViewModel,
    private val text : String
) : BaseDialog<DialogMessageBinding>(R.layout.dialog_message,mContext, lifecycle){

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvMessage.text = text

        initListener()
    }

    private fun initListener(){
        binding.btnOk.setOnClickListener {
            dismiss()
            cancel()
        }
    }

}