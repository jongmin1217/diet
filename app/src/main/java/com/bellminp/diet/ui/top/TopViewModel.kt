package com.bellminp.diet.ui.top

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.utils.SingleLiveEvent

class TopViewModel : BaseViewModel() {

    private val _backClick = SingleLiveEvent<Unit>()
    val backClick: LiveData<Unit> get() = _backClick

    val titleText = MutableLiveData<String>().default(DietApplication.mInstance.resources.getString(R.string.title_calendar))
    val btnBackVisible = MutableLiveData<Boolean>().default(false)

    fun btnBackClick(){
        _backClick.value = Unit
    }
}