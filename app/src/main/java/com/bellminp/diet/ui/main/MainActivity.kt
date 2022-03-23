package com.bellminp.diet.ui.main

import androidx.activity.viewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityMainBinding
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.intro.IntroViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }
}