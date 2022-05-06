package com.bellminp.diet.ui.dialog.yn

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.bellminp.diet.R
import com.bellminp.diet.databinding.DialogYnBinding
import com.bellminp.diet.ui.base.BaseDialog
import com.bellminp.diet.ui.data.DialogData
import com.bellminp.diet.ui.dialog.weight.BottomWeightViewModel

class YnDialog(
    mContext : Context,
    lifecycle : LifecycleOwner,
    private val viewModel: YnViewModel,
    private val data : DialogData,
    private val delete : ((position : Int)->Unit) = {}
) : BaseDialog<DialogYnBinding>(R.layout.dialog_yn,mContext, lifecycle){

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvMessage.text = data.title
        binding.btnCancel.text = data.leftText
        binding.btnOk.text = data.rightText
        binding.btnCancel.setTextColor(data.leftColor)
        binding.btnOk.setTextColor(data.rightColor)

        initListener()
    }

    private fun initListener(){
        binding.btnCancel.setOnClickListener {
            dismiss()
            cancel()
        }

        binding.btnOk.setOnClickListener {
            viewModel.okClick(data)
            delete(data.deleteData?.position?:-1)
            dismiss()
            cancel()
        }
    }

    override fun dismiss() {
        viewModel.destroyedDialog()
        super.dismiss()
    }
}