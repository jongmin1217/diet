package com.bellminp.diet.ui.body_slide.setting

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BodyImageData
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.DelegateLiveData
import com.bellminp.diet.utils.SingleLiveEvent
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class BodySlideSettingViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase
) : BaseViewModel() {

    private val _imageSet = SingleLiveEvent<ArrayList<FoodData>>()
    val imageSet: LiveData<ArrayList<FoodData>> get() = _imageSet

    val checkAll = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) check(Constants.CHECK_ALL,new)
    }.default(false)

    val checkRange = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) check(Constants.CHECK_RANGE,new)
    }.default(false)

    val startDate = DelegateLiveData<String>{ _, _ ->
        check(Constants.CHECK_RANGE,true)
    }.default(String())

    val endDate = DelegateLiveData<String>{ _, _ ->
        check(Constants.CHECK_RANGE,true)
    }.default(String())

    val check1 = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) check(Constants.CHECK_1,new)
    }.default(true)

    val check2 = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) check(Constants.CHECK_2,new)
    }.default(false)

    val check3 = DelegateLiveData<Boolean>{ old, new ->
        if(old != new) check(Constants.CHECK_3,new)
    }.default(false)


    val checkNext = MutableLiveData<Boolean>().default(false)


    @SuppressLint("SimpleDateFormat")
    private fun check(type : Int, new : Boolean){
        if(new){
            when(type){
                Constants.CHECK_ALL -> {
                    if(checkRange.value == true) checkRange.value = false
                    getBodyImage(Long.MIN_VALUE, Long.MAX_VALUE)
                }
                Constants.CHECK_RANGE ->{
                    if(checkAll.value == true) checkAll.value = false
                    if(startDate.value != String() && endDate.value != String()){
                        val start = startDate.value!!
                        val end = endDate.value!!
                        val dateFormat = SimpleDateFormat("yyyy:MM:dd")
                        val startReg = dateFormat.parse("${start.split(".")[0].toInt()}:${Utils.dateText(start.split(".")[1].toInt())}:${Utils.dateText(start.split(".")[2].toInt())}")!!.time
                        val endReg = dateFormat.parse("${end.split(".")[0].toInt()}:${Utils.dateText(end.split(".")[1].toInt())}:${Utils.dateText(end.split(".")[2].toInt())}")!!.time
                        getBodyImage(startReg,endReg)
                    }else{
                        checkNext.value = false
                        _imageSet.value = ArrayList()
                    }
                }

                Constants.CHECK_1 -> {
                    if(check2.value == true) check2.value = false
                    if(check3.value == true) check3.value = false
                }

                Constants.CHECK_2 -> {
                    if(check1.value == true) check1.value = false
                    if(check3.value == true) check3.value = false
                }

                Constants.CHECK_3 -> {
                    if(check2.value == true) check2.value = false
                    if(check1.value == true) check1.value = false
                }
            }
        }
    }

    private fun getBodyImage(startDate : Long, endDate : Long){
        getDietDataUseCase.getBodyImageRange(
            startDate,endDate,
            onSuccess = {
                checkNext.value = it.isNotEmpty()
                val list = ArrayList<FoodData>()

                for(i in it.indices){
                    list.add(FoodData(i.toLong(),it[i].getDateText(),it[i].body?:""))
                }

                _imageSet.value = list
            }, onError = {
                Timber.d("timber error getBodyImage $it")
            }
        )
    }
}