package com.bellminp.diet.ui.body_slide.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BodySlideData
import com.bellminp.diet.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BodySlideShowViewModel @Inject constructor() : BaseViewModel() {

    val image = MutableLiveData<String>().default(null)

    private val _showReplay = SingleLiveEvent<Unit>()
    val showReplay: LiveData<Unit> get() = _showReplay

    fun startSlide(data : BodySlideData){
        viewModelScope.launch(Dispatchers.IO){
            for(i in data.list){
                viewModelScope.launch(Dispatchers.Main){
                    image.value = i.image
                }
                delay(data.timer.toLong())
            }
            viewModelScope.launch(Dispatchers.Main){
                _showReplay.value = Unit
            }
        }
    }
}