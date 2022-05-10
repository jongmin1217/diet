package com.bellminp.diet.ui.main.fragment

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.BuildConfig
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.domain.usecase.GetProfileUseCase
import com.bellminp.diet.domain.usecase.GetWeightUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getWeightUseCase: GetWeightUseCase
) : BaseViewModel() {

    val profileData = MutableLiveData<ProfileData>().default(null)
    val profileImg = MutableLiveData<String>().default(if (File(profileUrl).exists()) profileUrl else null)

    val lastWeight = MutableLiveData<Float>().default(null)

    fun getProfile(){
        getProfileUseCase.observableProfile(
            onSuccess = {
                profileImg.value = if (File(profileUrl).exists()) profileUrl else null
                if(it.isNotEmpty()) profileData.value = it[0]
            }, onError = {
                Timber.d("timber getProfile error $it")
            }
        )

        getWeightUseCase.observableLastWeight(
            onSuccess = {
                lastWeight.value = if(it.isEmpty()) null else it[0]
            }, onError = {
                Timber.d("timber observableLastWeight error $it")
            }
        )
    }
}