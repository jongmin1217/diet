package com.bellminp.diet.ui.dialog

import androidx.lifecycle.LiveData
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BottomSheetData
import com.bellminp.diet.ui.data.DateSelectData
import com.bellminp.diet.utils.SingleLiveEvent

class BottomDateViewModel : BaseViewModel() {

    private val _dateSelect = SingleLiveEvent<DateSelectData>()
    val dateSelect: LiveData<DateSelectData> get() = _dateSelect

    fun okClick(year : Int, month : Int, day : Int){
        _dateSelect.value = DateSelectData(year, month, day)
    }
}