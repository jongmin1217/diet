package com.bellminp.diet.ui.dialog

import androidx.lifecycle.LiveData
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BottomSheetData
import com.bellminp.diet.utils.SingleLiveEvent

class BottomBasicViewModel : BaseViewModel() {

    private val _bottomSheetData = SingleLiveEvent<BottomSheetData>()
    val bottomSheetData: LiveData<BottomSheetData> get() = _bottomSheetData


    fun bottomSheetClick(bottomSheetData : BottomSheetData){
        _bottomSheetData.value = bottomSheetData
    }
}