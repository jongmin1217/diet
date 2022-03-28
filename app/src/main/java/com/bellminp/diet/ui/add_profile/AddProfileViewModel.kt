package com.bellminp.diet.ui.add_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.DelegateLiveData
import com.bellminp.diet.utils.Utils
import kotlinx.coroutines.*
import java.io.File

class AddProfileViewModel : BaseViewModel() {

    val checkPost = MutableLiveData<Boolean>().default(false)
    val edit = MutableLiveData<Boolean>().default(false)

    val profileImg = MutableLiveData<String>().default(null)

    val birth = DelegateLiveData<String> { _, _ ->
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
            birth.value?.isNotEmpty() ?: false && (checkFemale.value == true || checkMale.value == true) && (checkBulkUp.value == true || checkDiet.value == true) && nickname.value?.isNotEmpty() ?: false
                    && height.value?.isNotEmpty() ?: false && initWeight.value?.isNotEmpty() ?: false && goalWeight.value?.isNotEmpty() ?: false
    }

    @DelicateCoroutinesApi
    fun postClick(){
        showLoading(true)

        GlobalScope.launch(Dispatchers.Default) {

            if(profileImg.value != null) Utils.saveProfile(Uri.parse(profileImg.value))
            else Utils.deleteProfile()

            birth.value?.let {
                DietApplication.mInstance.shared.setBirth(it)
            }

            when{
                checkFemale.value == true -> DietApplication.mInstance.shared.setGender(0)
                checkMale.value == true -> DietApplication.mInstance.shared.setGender(1)
            }

            when{
                checkDiet.value == true -> DietApplication.mInstance.shared.setType(0)
                checkBulkUp.value == true -> DietApplication.mInstance.shared.setType(1)
            }

            nickname.value?.let {
                DietApplication.mInstance.shared.setNickname(it)
            }

            height.value?.let {
                DietApplication.mInstance.shared.setHeight(it.toFloat())
            }

            initWeight.value?.let {
                DietApplication.mInstance.shared.setInitWeight(it.toFloat())
            }

            goalWeight.value?.let {
                DietApplication.mInstance.shared.setGoalWeight(it.toFloat())
            }

            GlobalScope.launch(Dispatchers.Main){
                showLoading(false)
                completePost()
            }
        }
    }

    private fun completePost(){
        DietApplication.mInstance.sendProfileEvent(Constants.ADD)
        showToast(DietApplication.mInstance.resources.getString(R.string.add_profile_complete))
        finishView()
    }

    fun initEditData(){
        val shared = DietApplication.mInstance.shared

        if(File(profileUrl).exists()) profileImg.value = profileUrl

        birth.value = shared.getBirth()

        if(shared.getGender() == 0) checkFemale.value = true
        else checkMale.value = true

        if(shared.getType() == 0) checkDiet.value = true
        else checkBulkUp.value = true

        nickname.value = shared.getNickname()

        height.value = shared.getHeight().toString()

        initWeight.value = shared.getInitWeight().toString()

        goalWeight.value = shared.getGoalWeight().toString()
    }


}