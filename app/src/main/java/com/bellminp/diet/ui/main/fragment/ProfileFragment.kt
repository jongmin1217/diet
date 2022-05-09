package com.bellminp.diet.ui.main.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentGraphBinding
import com.bellminp.diet.databinding.FragmentProfileBinding
import com.bellminp.diet.ui.add_profile.AddProfileActivity
import com.bellminp.diet.ui.base.BaseFragment
import com.bellminp.diet.utils.Constants
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
        viewModel.getProfile()
    }


    private fun initListener(){
        binding.btnAddProfile.setOnClickListener {
            val intent = Intent(context,AddProfileActivity::class.java)
            intent.putExtra(Constants.TYPE, if(viewModel.profileData.value != null) Constants.EDIT else Constants.ADD)
            viewModel.profileData.value?.let { data -> intent.putExtra(Constants.DATA,data) }
            startActivity(intent)
        }
    }
}