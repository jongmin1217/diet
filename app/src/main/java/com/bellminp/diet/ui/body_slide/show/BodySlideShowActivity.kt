package com.bellminp.diet.ui.body_slide.show

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityBodySlideShowBinding
import com.bellminp.diet.ui.adapter.FoodImageAdapter
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.body_slide.setting.BodySlideSettingViewModel
import com.bellminp.diet.ui.data.BodySlideData
import com.bellminp.diet.ui.dialog.date.BottomDateViewModel
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.BindAdapter
import com.bellminp.diet.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BodySlideShowActivity : BaseActivity<ActivityBodySlideShowBinding,BodySlideShowViewModel>(R.layout.activity_body_slide_show) {
    override val viewModel by viewModels<BodySlideShowViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.getSerializableExtra(Constants.DATA) as BodySlideData

        viewModel.image.value = data.list[0].image

        initListener(data)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(viewModel){
            showReplay.observe(binding.lifecycleOwner!!,{
                binding.btnStart.background = resources.getDrawable(R.drawable.icn_replay,null)
                binding.btnStart.visibility = View.VISIBLE
            })
        }
    }

    private fun initListener(data : BodySlideData){
        binding.tvClose.setOnClickListener {
            finish()
        }

        binding.btnStart.setOnClickListener {
            binding.btnStart.visibility = View.GONE
            viewModel.startSlide(data)
        }
    }
}