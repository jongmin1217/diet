package com.bellminp.diet.ui.body_image

import androidx.lifecycle.LiveData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BodyImageData
import com.bellminp.diet.ui.data.DeleteDietData
import com.bellminp.diet.ui.data.FoodImageData
import com.bellminp.diet.ui.data.FoodImageListData
import com.bellminp.diet.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BodyImageViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase
) : BaseViewModel() {

    private var pagingData : Long = Long.MAX_VALUE
    var loading = false

    private val _bodyImageList = SingleLiveEvent<BodyImageData>()
    val bodyImageList: LiveData<BodyImageData> get() = _bodyImageList

    fun getBodyImage(paging : Boolean){
        if(!paging) pagingData = Long.MAX_VALUE
        getDietDataUseCase.singleBodyImage(
            pagingData,
            onSuccess = {

                Timber.d("timber $it")
                _bodyImageList.value = BodyImageData(paging,ArrayList(it))
                if(it.isNotEmpty()){
                    pagingData = it[it.size-1].regDate?:Long.MAX_VALUE
                }
            }, onError = {
                Timber.d("timber error getBodyImage $it")
            }, onStart = { loading = true }, onFinished = { loading = false }
        )
    }

    fun getAllBody(dietData: DietData){
        getDietDataUseCase.allBodyImage(
            onSuccess = {
                val index = it.indexOfFirst { item -> item == dietData }
                if(index != -1){
                    val list = ArrayList<FoodData>()
                    for(i in it.indices){
                        list.add(FoodData(i.toLong(),it[i].getDateText(),it[i].body?:""))
                    }
                    goFoodDetail(FoodImageData(-1,list,index))
                }
            }, onError = {
                Timber.d("timber error getAllBody $it")
            }
        )
    }
}