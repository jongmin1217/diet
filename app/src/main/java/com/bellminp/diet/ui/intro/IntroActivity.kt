package com.bellminp.diet.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityIntroBinding
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding,IntroViewModel>(R.layout.activity_intro) {
    override val viewModel by viewModels<IntroViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ivIntro.run {
            postDelayed({
                startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                finish()
            }, 2000)
        }


    }
}