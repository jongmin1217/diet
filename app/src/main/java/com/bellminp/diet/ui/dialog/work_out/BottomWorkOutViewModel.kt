package com.bellminp.diet.ui.dialog.work_out

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.model.WorkOutData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class BottomWorkOutViewModel @Inject constructor(
    private val addDietDataUseCase: AddDietDataUseCase
) : BaseViewModel() {
    val workOutType = MutableLiveData<String>().default(String())

    @SuppressLint("SimpleDateFormat")
    fun addWorkOut(id: Long?, date: DateData, list : ArrayList<WorkOutData>?, position : Int?, time : String) {
        workOutType.value?.let { type ->
            val workOutId = if(list == null || list.size == 0) 0 else list[list.size-1].id + 1
            val workOutList = ArrayList<WorkOutData>()

            val dateFormat = SimpleDateFormat("yyyy:MM:dd-HH:mm")
            val startDate = dateFormat.parse("${date.year}:${Utils.dateText(date.month)}:${Utils.dateText(date.day)}-${time.split("|")[0]}")
            val endDate = dateFormat.parse("${date.year}:${Utils.dateText(date.month)}:${Utils.dateText(date.day)}-${time.split("|")[1]}")
            val startRegDate = startDate!!.time
            val endRegDate = endDate!!.time

            if(position == null){
                if(list != null) workOutList.addAll(list)
                workOutList.add(WorkOutData(workOutId,type,time,startRegDate,endRegDate))
            }else{
                list?.let { list ->
                    list[position] = WorkOutData(workOutId,type,time,startRegDate,endRegDate)
                    workOutList.addAll(list)
                }
            }

            addWorkOutList(id,date,workOutList)
        }
    }

    private fun addWorkOutList(id: Long?, date: DateData, list : ArrayList<WorkOutData>){
        if (id == null) {
            addDietDataUseCase.addDietData(
                DietData(0, date.year, date.month, date.day, workOutList = list),
                onSuccess = {
                    dialogDismiss()
                }
            )
        } else {
            addDietDataUseCase.editWorkOut(
                id,
                list,
                onSuccess = {
                    dialogDismiss()
                }
            )
        }
    }
}