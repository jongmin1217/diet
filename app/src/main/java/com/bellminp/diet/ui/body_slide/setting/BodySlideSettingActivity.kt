package com.bellminp.diet.ui.body_slide.setting

import android.os.Bundle
import androidx.activity.viewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityBodySlideSettingBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.adapter.BodyImageAdapter
import com.bellminp.diet.ui.adapter.FoodImageAdapter
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.body_image.BodyImageViewModel
import com.bellminp.diet.ui.dialog.date.BottomDateDialog
import com.bellminp.diet.ui.dialog.date.BottomDateViewModel
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.BindAdapter
import com.bellminp.diet.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BodySlideSettingActivity : BaseActivity<ActivityBodySlideSettingBinding,BodySlideSettingViewModel>(R.layout.activity_body_slide_setting) {
    override val viewModel by viewModels<BodySlideSettingViewModel>()
    private val topViewModel by viewModels<TopViewModel>()
    private val dateViewModel by viewModels<BottomDateViewModel>()

    lateinit var adapter : FoodImageAdapter

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(topViewModel){
            titleText.value = resources.getString(R.string.slide_range_title)
            btnBackVisible.value = true
        }

        initAdapter()
        initListener()
    }

    private fun initAdapter(){
        adapter = FoodImageAdapter(viewModel)
        BindAdapter.dateFoodAdapter(binding.recyclerviewBody, adapter)
    }

    private fun initListener(){
        binding.btnStartDate.setOnClickListener {
            BottomDateDialog(if(viewModel.startDate.value == "") null else viewModel.startDate.value, Constants.START_DATE).show(supportFragmentManager, "date")
        }

        binding.btnEndDate.setOnClickListener {
            BottomDateDialog(if(viewModel.endDate.value == "") null else viewModel.endDate.value, Constants.END_DATE).show(supportFragmentManager, "date")
        }
    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(topViewModel){
            backClick.observe(binding.lifecycleOwner!!,{
                finish()
            })
        }

        with(viewModel){
            imageSet.observe(binding.lifecycleOwner!!,{ items->
                adapter.items = items
            })
        }

        with(dateViewModel){
            dateSelect.observe(binding.lifecycleOwner!!, { birth ->
                when(birth.type){
                    Constants.START_DATE -> viewModel.startDate.value = String.format("%d.%d.%d",birth.year,birth.month,birth.day)
                    Constants.END_DATE -> viewModel.endDate.value = String.format("%d.%d.%d",birth.year,birth.month,birth.day)
                }

            })
        }
    }
}