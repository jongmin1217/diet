package com.bellminp.diet.ui.main.fragment

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.BuildConfig
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.base.BaseViewModel
import java.io.File

class ProfileViewModel : BaseViewModel() {

    val nickname = MutableLiveData<String>().default(DietApplication.mInstance.shared.getNickname())
    val profileImg = MutableLiveData<String>().default(if (File(profileUrl).exists()) profileUrl else null)

    fun refreshProfile(){
        nickname.value = DietApplication.mInstance.shared.getNickname()
        profileImg.value = if (File(profileUrl).exists()) profileUrl else null
    }
}