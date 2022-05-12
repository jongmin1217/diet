package com.bellminp.diet.ui.main.fragment

import androidx.lifecycle.LiveData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.domain.usecase.GetProfileUseCase
import com.bellminp.diet.domain.usecase.GetWeightUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase
) : BaseViewModel() {

    private val _initWeight = SingleLiveEvent<List<DietData>>()
    val initWeight: LiveData<List<DietData>> get() = _initWeight


    fun initWeight(year: Int, month: Int) {
        getDietDataUseCase.clearDisposable()
        getDietDataUseCase.observableDietDataList(year, month,
            onSuccess = {
                _initWeight.value = it
            }, onError = {
                Timber.d("timber initWeight error $it")
            })
    }
}