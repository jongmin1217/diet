package com.bellminp.diet.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.domain.usecase.GetProfileUseCase
import com.bellminp.diet.domain.usecase.GetWeightUseCase
import com.bellminp.diet.ui.adapter.CalendarAdapter
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.utils.SingleLiveEvent
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase,
    private val getWeightUseCase: GetWeightUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel() {

    val lastWeight = MutableLiveData<Float>().default(null)
    val profileData = MutableLiveData<ProfileData>().default(null)

    private val _initCalendar = SingleLiveEvent<ArrayList<CalendarData>>()
    val initCalendar: LiveData<ArrayList<CalendarData>> get() = _initCalendar

    private val _goWriteType = SingleLiveEvent<DateData>()
    val goWriteType: LiveData<DateData> get() = _goWriteType

    private val _noDate = SingleLiveEvent<Unit>()
    val noDate: LiveData<Unit> get() = _noDate


    fun initCalendar(year: Int, month: Int) {
        clearDietDataObserve()
        getDietDataUseCase.observableDietDataList(year, month,
            onSuccess = {
                initCalendarItems(
                    Utils.lastDay(year, month),
                    Utils.getDateDay(String.format("%d%s%s", year, Utils.dateText(month), "01"), "yyyyMMdd"),
                    it,
                    year,
                    month
                ).apply {
                    _initCalendar.value = this
                }
            }, onError = {
                Timber.d("timber observableDietDataList $it")
            }
        )
    }

    private fun initCalendarItems(lastDay: Int, startDay: Int, data : List<DietData>, year: Int,month: Int): ArrayList<CalendarData> {
        Timber.d("timber data $data")

        val list = ArrayList<CalendarData>()

        if (startDay != 1) {
            for (i in 0 until startDay - 1) {
                list.add(CalendarData(i, false, year,month,null, null))
            }
        }

        for (i in 0 until lastDay) {
            val index = data.indexOfFirst { it.day == i+1 }

            list.add(
                CalendarData(
                    if (list.isEmpty()) 0 else list[list.size - 1].id + 1,
                    true,
                    year,
                    month,
                    (i + 1).toString(),
                    if(index == -1) null else data[index]
                )
            )
        }

        return list
    }

    fun initLastWeight(){
        getWeightUseCase.observableLastWeight(
            onSuccess = {
                lastWeight.value = if(it.isEmpty()) null else it[0]
            }, onError = {
                Timber.d("timber error $it")
            }
        )
    }

    fun initProfile(){
        getProfileUseCase.observableProfile(
            onSuccess = {
                profileData.value = if(it.isEmpty()) null else it[0]
            }, onError = {
                Timber.d("timber error $it")
            }
        )
    }

    fun clearDietDataObserve(){
        getDietDataUseCase.clearDisposable()
    }

    fun calendarClick(calendarData: CalendarData){
        if(calendarData.year == Utils.getYear() && calendarData.month == Utils.getMonth() && calendarData.text?.toInt()?:0 > Utils.getDay()) _noDate.value = Unit
        else _goWriteType.value = DateData(calendarData.year,calendarData.month,calendarData.text?.toInt()?:0)
    }
}