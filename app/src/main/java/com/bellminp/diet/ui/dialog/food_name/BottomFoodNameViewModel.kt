package com.bellminp.diet.ui.dialog.food_name

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.SingleLiveEvent
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BottomFoodNameViewModel @Inject constructor(
    private val addDietDataUseCase: AddDietDataUseCase
) : BaseViewModel() {

    val name = MutableLiveData<String>().default(String())
    val photoUri = MutableLiveData<Uri>().default(null)
    val photoUrl = MutableLiveData<String>().default(null)

    fun addFood(id: Long?, date: DateData, list : ArrayList<FoodData>?, position : Int?) {
        name.value?.let { name ->
            viewModelScope.launch(Dispatchers.IO) {
                val foodId = if(list == null || list.size == 0) 0 else list[list.size-1].id + 1
                val foodList = ArrayList<FoodData>()

                if(position == null){
                    photoUri.value?.let { uri ->
                        val url = Utils.saveFood(uri)

                        url?.let {
                            if(list != null) foodList.addAll(list)
                            foodList.add(FoodData(foodId,name,url))
                        }
                    }

                }else{
                    list?.let { list ->
                        list[position] = FoodData(list[position].id,name,list[position].image)
                        foodList.addAll(list)
                    }
                }

                addFoodList(id, date, foodList)
            }
        }

    }

    private fun addFoodList(id: Long?, date: DateData, list : ArrayList<FoodData>){
        if (id == null) {
            addDietDataUseCase.addDietData(
                DietData(0, date.year, date.month, date.day, food = list)
            )
        } else {
            addDietDataUseCase.editFood(
                id,
                list
            )
        }
    }

    override fun destroyedBottomDialog() {
        addDietDataUseCase.clearDisposable()
    }
}