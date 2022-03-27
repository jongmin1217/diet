package com.bellminp.diet.ui.main.fragment

import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentProfileBinding
import com.bellminp.diet.databinding.FragmentSlideBinding
import com.bellminp.diet.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SlideFragment : BaseFragment<FragmentSlideBinding, SlideViewModel>(R.layout.fragment_slide) {
    override val viewModel by activityViewModels<SlideViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }
}