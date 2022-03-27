package com.bellminp.diet.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentGraphBinding
import com.bellminp.diet.databinding.FragmentProfileBinding
import com.bellminp.diet.ui.add_profile.AddProfileActivity
import com.bellminp.diet.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModel by activityViewModels<ProfileViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener(){
        binding.btnAddProfile.setOnClickListener {
            startActivity(Intent(context,AddProfileActivity::class.java))
        }
    }
}