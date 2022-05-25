package com.bellminp.diet.ui.main.fragment

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.R
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.domain.usecase.GetProfileUseCase
import com.bellminp.diet.domain.usecase.GetWeightUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.utils.SingleLiveEvent
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : BaseViewModel() {

    private val _initWeight = SingleLiveEvent<List<DietData>>()
    val initWeight: LiveData<List<DietData>> get() = _initWeight

    val startWorkDate = MutableLiveData<String>().default(null)
    val endWorkDate = MutableLiveData<String>().default(null)

    fun initWeight(year: Int, month: Int) {
        getDietDataUseCase.clearDisposable()
        getDietDataUseCase.observableDietDataList(year, month,
            onSuccess = {
                _initWeight.value = it
            }, onError = {
                Timber.d("timber initWeight error $it")
            })
    }

    @SuppressLint("SimpleDateFormat")
    fun initWorkDate(){
        getProfileUseCase.observableProfile(
            onSuccess = {
                if(it.isEmpty()){
                    startWorkDate.value = null
                    endWorkDate.value = null
                }else{
                    val startDate = it[0].startDate
                    val endDate = it[0].endDate

                    val dateFormat = SimpleDateFormat("yyyy:MM:dd")
                    val start = dateFormat.parse("${startDate.split(".")[0]}:${Utils.dateText(startDate.split(".")[1].toInt())}:${Utils.dateText(startDate.split(".")[2].toInt())}")
                    val end = dateFormat.parse("${endDate.split(".")[0]}:${Utils.dateText(endDate.split(".")[1].toInt())}:${Utils.dateText(endDate.split(".")[2].toInt())}")
                    val startReg = start!!.time
                    val endReg = end!!.time

                    val now = dateFormat.parse("${Utils.getYear()}:${Utils.dateText(Utils.getMonth())}:${Utils.dateText(Utils.getDay())}")!!.time

                    val startWork = (now - startReg) / (1000 *60 * 60 * 24)
                    val endWork = (now - endReg) / (1000 *60 * 60 * 24)

                    startWorkDate.value = String.format("D${if(startWork>=0) "+" else ""}%d 운동 시작",if(startWork>=0) startWork.toInt()+1 else startWork.toInt())
                    endWorkDate.value = String.format("D${if(endWork>=0) "+" else ""}%d 운동 종료",if(endWork>=0) endWork.toInt()+1 else endWork.toInt())
                }
            }, onError = {
                Timber.d("timber initWorkDate error $it")
            }
        )
    }
}