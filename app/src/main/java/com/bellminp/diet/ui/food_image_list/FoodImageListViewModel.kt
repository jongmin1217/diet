package com.bellminp.diet.ui.food_image_list

import androidx.lifecycle.LiveData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.FoodImageData
import com.bellminp.diet.ui.data.FoodImageListData
import com.bellminp.diet.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FoodImageListViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase
) : BaseViewModel() {

    private var pagingData : Long = Long.MAX_VALUE
    var loading = false

    private val _foodImageList = SingleLiveEvent<FoodImageListData>()
    val foodImageList: LiveData<FoodImageListData> get() = _foodImageList

    fun getFoodImage(paging : Boolean){
        if(!paging) pagingData = Long.MAX_VALUE
        getDietDataUseCase.singleFoodImage(
            pagingData,
            onSuccess = {
                Timber.d("timber $it")
                _foodImageList.value = FoodImageListData(paging,ArrayList(it))
                if(it.isNotEmpty()){
                    pagingData = it[it.size-1].regDate?:Long.MAX_VALUE
                }
            }, onError = {
                Timber.d("timber error getFoodImage $it")
            }, onStart = { loading = true }, onFinished = { loading = false }
        )
    }

    override fun foodImageClick(position: Int, foodData : FoodData) {
        getDietDataUseCase.allFoodImage(
            onSuccess = {
                Timber.d("timber $it")
                val list = ArrayList<FoodData>()
                for (i in it){
                    i.food?.let { food ->
                        list.addAll(food)
                    }
                }

                val index = list.indexOfFirst {data -> data == foodData }
                if(index != -1) goFoodDetail(FoodImageData(-1,list,index))
            }, onError = {
                Timber.d("timber error foodImageClick $it")
            }
        )
    }
}