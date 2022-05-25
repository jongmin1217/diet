package com.bellminp.diet.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentProfileBinding
import com.bellminp.diet.databinding.FragmentSlideBinding
import com.bellminp.diet.ui.add_profile.AddProfileActivity
import com.bellminp.diet.ui.base.BaseFragment
import com.bellminp.diet.ui.body_image.BodyImageActivity
import com.bellminp.diet.ui.body_slide.setting.BodySlideSettingActivity
import com.bellminp.diet.ui.food_image_list.FoodImageListActivity
import com.bellminp.diet.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlideFragment : BaseFragment<FragmentSlideBinding, SlideViewModel>(R.layout.fragment_slide) {
    override val viewModel by activityViewModels<SlideViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }
    
    private fun initListener(){
        binding.layoutFood.setOnClickListener {
            startActivity(Intent(context, FoodImageListActivity::class.java))
        }

        binding.layoutBody.setOnClickListener {
            startActivity(Intent(context, BodyImageActivity::class.java))
        }

        binding.layoutBodySlide.setOnClickListener {
            startActivity(Intent(context, BodySlideSettingActivity::class.java))
        }
    }
}