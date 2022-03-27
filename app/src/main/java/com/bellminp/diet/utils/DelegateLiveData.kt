package com.bellminp.diet.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import timber.log.Timber


@SuppressWarnings("WeakerAccess")
class DelegateLiveData<T>(
    private val onChange: (T,T) -> Unit
) : MutableLiveData<T>() {

    override fun setValue(value: T) {
        val beforeValue = getValue()
        super.setValue(value)
        beforeValue?.let {
            onChange(it,value)
        }
    }


}