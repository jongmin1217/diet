package com.bellminp.diet.ui.write_type

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WriteTypeViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase
)  : BaseViewModel() {

    var date : DateData? = null
    val dietData = MutableLiveData<DietData>().default(null)

    fun getDietData(date : DateData){
        this.date = date
        getDietDataUseCase.observableDietData(date.year, date.month, date.day,
            onSuccess = {
                dietData.value = it
            }, onError = {
                Timber.d("timber observableDietData $it")
            }
        )
    }

    fun clearDietDataObserve(){
        getDietDataUseCase.clearDisposable()
    }
}