package com.bellminp.diet.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bellminp.diet.BuildConfig
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.ui.data.BottomSheetData
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.data.FoodImageData
import com.bellminp.diet.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    fun <T : Any?> MutableLiveData<T>.default(initialValue: T?) = apply { setValue(initialValue) }

    private val _layoutClick = SingleLiveEvent<Unit>()
    val layoutClick: LiveData<Unit> get() = _layoutClick

    private val _showToast = SingleLiveEvent<String>()
    val showToast: LiveData<String> get() = _showToast

    private val _showLoading = SingleLiveEvent<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _finishView = SingleLiveEvent<Unit>()
    val finishView: LiveData<Unit> get() = _finishView

    private val _dialogDismiss = SingleLiveEvent<Unit>()
    val dialogDismiss: LiveData<Unit> get() = _dialogDismiss

    private val _goFoodDetail = SingleLiveEvent<FoodImageData>()
    val goFoodDetail: LiveData<FoodImageData> get() = _goFoodDetail


    private val compositeDisposable = CompositeDisposable()

    val profileUrl = "data/data/" + BuildConfig.APPLICATION_ID + "/app_profile_image/profile.jpg"

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun goFoodDetail(value : FoodImageData){
        _goFoodDetail.value = value
    }

    fun layoutClick(){
        _layoutClick.value = Unit
    }

    fun showToast(value: String) {
        _showToast.value = value
    }

    fun showLoading(value: Boolean) {
        _showLoading.value = value
    }

    fun finishView(){
        _finishView.value = Unit
    }

    fun dialogDismiss(){
        _dialogDismiss.value = Unit
    }

    open fun foodImageClick(position: Int, foodData : FoodData){}

    open fun destroyedBottomDialog(){}

}