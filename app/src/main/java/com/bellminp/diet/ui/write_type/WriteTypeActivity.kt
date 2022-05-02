package com.bellminp.diet.ui.write_type

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityWriteTypeBinding
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.dialog.add_content.BottomAddContentDialog
import com.bellminp.diet.ui.dialog.content_detail.BottomContentDetailDialog
import com.bellminp.diet.ui.dialog.weight.BottomWeightDialog
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteTypeActivity : BaseActivity<ActivityWriteTypeBinding,WriteTypeViewModel>(R.layout.activity_write_type) {
    override val viewModel by viewModels<WriteTypeViewModel>()
    private val topViewModel by viewModels<TopViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(intent.hasExtra(Constants.DATA)){
            val date = intent.getSerializableExtra(Constants.DATA) as DateData
            viewModel.getDietData(date)
            topViewModel.btnBackVisible.value = true
            topViewModel.titleText.value = String.format("%d.%s.%s",date.year,Utils.dateText(date.month),Utils.dateText(date.day))
        }

        initListener()
    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(topViewModel) {
            backClick.observe(binding.lifecycleOwner!!, {
                finish()
            })
        }
    }

    private fun initListener(){
        binding.btnWeightAdd.setOnClickListener {
            with(viewModel){
                date?.let { date ->
                    BottomWeightDialog(
                        dietData.value?.weight,
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "weight")
                }
            }
        }

        binding.btnContentAdd.setOnClickListener {
            with(viewModel){
                date?.let { date ->
                    BottomAddContentDialog(
                        dietData.value?.content,
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "content")
                }
            }
        }

        binding.tvContentValue.setOnClickListener {
            viewModel.dietData.value?.content?.let{ text ->
                BottomContentDetailDialog(text).show(supportFragmentManager, "content_detail")
            }
        }
    }

    override fun onDestroy() {
        viewModel.clearDietDataObserve()
        super.onDestroy()
    }
}