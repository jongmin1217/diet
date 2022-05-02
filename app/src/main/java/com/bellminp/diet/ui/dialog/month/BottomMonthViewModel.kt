package com.bellminp.diet.ui.dialog.month

import androidx.lifecycle.LiveData
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DateSelectData
import com.bellminp.diet.ui.data.MonthSelectData
import com.bellminp.diet.utils.SingleLiveEvent

class BottomMonthViewModel : BaseViewModel() {

    private val _monthSelect = SingleLiveEvent<MonthSelectData>()
    val monthSelect: LiveData<MonthSelectData> get() = _monthSelect

    fun okClick(year : Int, month : Int){
        _monthSelect.value = MonthSelectData(year,month)
    }
}