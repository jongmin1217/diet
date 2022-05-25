package com.bellminp.diet.ui.add_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.domain.usecase.AddProfileUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.DelegateLiveData
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddProfileViewModel @Inject constructor(
    private val addProfileUseCase: AddProfileUseCase
) : BaseViewModel() {

    val checkPost = MutableLiveData<Boolean>().default(false)
    val edit = MutableLiveData<Boolean>().default(false)

    val profileImg = MutableLiveData<String>().default(null)

    val birth = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    val startWorkOut = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    val endWorkOut = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    val checkFemale = DelegateLiveData<Boolean> { old, new ->
        if (old != new) checkGender(new, 0)
    }.default(false)

    val checkMale = DelegateLiveData<Boolean> { old, new ->
        if (old != new) checkGender(new, 1)
    }.default(false)

    val checkDiet = DelegateLiveData<Boolean> { old, new ->
        if (old != new) checkType(new, 0)
    }.default(false)

    val checkBulkUp = DelegateLiveData<Boolean> { old, new ->
        if (old != new) checkType(new, 1)
    }.default(false)

    val nickname = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    val height = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    val initWeight = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    val goalWeight = DelegateLiveData<String> { _, _ ->
        checkPost()
    }.default("")

    private fun checkGender(new: Boolean, type: Int) {
        if (new) {
            if (type == 0) checkMale.value = false
            else checkFemale.value = false
        }
        checkPost()
    }

    private fun checkType(new: Boolean, type: Int) {
        if (new) {
            if (type == 0) checkBulkUp.value = false
            else checkDiet.value = false
        }
        checkPost()
    }

    private fun checkPost() {
        checkPost.value =
            birth.value?.isNotEmpty() ?: false && startWorkOut.value?.isNotEmpty() ?: false && endWorkOut.value?.isNotEmpty() ?: false && (checkFemale.value == true || checkMale.value == true) && (checkBulkUp.value == true || checkDiet.value == true) && nickname.value?.isNotEmpty() ?: false
                    && height.value?.isNotEmpty() ?: false && initWeight.value?.isNotEmpty() ?: false && goalWeight.value?.isNotEmpty() ?: false
    }

    fun postClick() {
        showLoading(true)

        viewModelScope.launch(Dispatchers.Default) {

            if (profileImg.value != null) Utils.saveProfile(Uri.parse(profileImg.value))
            else Utils.deleteProfile()

            ProfileData(
                1,
                nickname.value ?: "",
                birth.value ?: "",
                height.value?.toFloat() ?: 0F,
                when {
                    checkFemale.value == true -> 0
                    checkMale.value == true -> 1
                    else -> 0
                },
                when {
                    checkDiet.value == true -> 0
                    checkBulkUp.value == true -> 1
                    else -> 0
                },
                initWeight.value?.toFloat() ?: 0F,
                goalWeight.value?.toFloat() ?: 0F,
                startWorkOut.value?:"",
                endWorkOut.value?:""
            ).apply {
                if (edit.value == true) {
                    addProfileUseCase.editProfile(this,
                        onFinished = {
                            showLoading(false)
                            completePost()
                        })
                }else{
                    addProfileUseCase.addProfile(this,
                        onFinished = {
                            showLoading(false)
                            completePost()
                        })
                }

            }
        }
    }

    private fun completePost() {
        if(edit.value == true) showToast(DietApplication.mInstance.resources.getString(R.string.edit_profile_complete))
        else showToast(DietApplication.mInstance.resources.getString(R.string.add_profile_complete))
        finishView()
    }

    fun initEditData(data : ProfileData) {
        if (File(profileUrl).exists()) profileImg.value = profileUrl

        birth.value = data.birth
        startWorkOut.value = data.startDate
        endWorkOut.value = data.endDate

        if (data.gender == 0) checkFemale.value = true
        else checkMale.value = true

        if (data.type == 0) checkDiet.value = true
        else checkBulkUp.value = true

        nickname.value = data.nickname

        height.value = data.height.toString()

        initWeight.value = data.initWeight.toString()

        goalWeight.value = data.goalWeight.toString()
    }


}