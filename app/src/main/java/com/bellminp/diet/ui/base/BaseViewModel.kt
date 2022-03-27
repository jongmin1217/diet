package com.bellminp.diet.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bellminp.diet.ui.data.BottomSheetData
import com.bellminp.diet.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    fun <T : Any?> MutableLiveData<T>.default(initialValue: T?) = apply { setValue(initialValue) }

    private val _layoutClick = SingleLiveEvent<Unit>()
    val layoutClick: LiveData<Unit> get() = _layoutClick

    private val _showToast = SingleLiveEvent<String>()
    val showToast: LiveData<String> get() = _showToast

    private val _showLoading = SingleLiveEvent<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val compositeDisposable = CompositeDisposable()


    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
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
}