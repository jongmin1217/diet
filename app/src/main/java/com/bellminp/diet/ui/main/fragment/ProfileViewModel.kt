package com.bellminp.diet.ui.main.fragment

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.base.BaseViewModel

class ProfileViewModel : BaseViewModel() {

    val nickname = MutableLiveData<String>().default(DietApplication.mInstance.shared.getNickname())
    val profileImg = MutableLiveData<String>().default(DietApplication.mInstance.shared.getProfileImg())
}