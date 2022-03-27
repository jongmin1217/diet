package com.bellminp.diet.ui.add_profile

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.utils.DelegateLiveData
import timber.log.Timber

class AddProfileViewModel : BaseViewModel() {

    val checkPost = MutableLiveData<Boolean>().default(false)
    val edit = MutableLiveData<Boolean>().default(false)

    val profileImg = MutableLiveData<String>().default(null)

    val birth = DelegateLiveData<String>{ _, _ ->
        checkPost()
    }.default("")

    val checkFemale = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) checkGender(new,0)
    }.default(false)

    val checkMale = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) checkGender(new,1)
    }.default(false)

    val nickname = DelegateLiveData<String>{ _, _ ->
        checkPost()
    }.default("")

    val height = DelegateLiveData<String>{ _, _ ->
        checkPost()
    }.default("")

    val initWeight = DelegateLiveData<String>{ _, _ ->
        checkPost()
    }.default("")

    val goalWeight = DelegateLiveData<String>{ _, _ ->
        checkPost()
    }.default("")

    private fun checkGender(new : Boolean, type : Int){
        if(new){
            if(type == 0) checkMale.value = false
            else checkFemale.value = false
        }
        checkPost()
    }

    private fun checkPost(){
        checkPost.value = birth.value?.isNotEmpty()?:false && (checkFemale.value == true || checkMale.value == true) && nickname.value?.isNotEmpty()?:false
                && height.value?.isNotEmpty()?:false && initWeight.value?.isNotEmpty()?:false && goalWeight.value?.isNotEmpty()?:false
    }



}