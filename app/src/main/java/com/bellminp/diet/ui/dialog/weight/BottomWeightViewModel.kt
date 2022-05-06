package com.bellminp.diet.ui.dialog.weight

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DateData
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BottomWeightViewModel @Inject constructor(
    private val addDietDataUseCase: AddDietDataUseCase
) : BaseViewModel() {

    val weight = MutableLiveData<String>().default(String())

    fun addWeight(id: Long?, date: DateData) {
        weight.value?.let {
            if (id == null) {
                addDietDataUseCase.addDietData(
                    DietData(0, date.year, date.month, date.day, weight = it.toFloat()),
                    onSuccess = {
                        dialogDismiss()
                    },
                    onError = { error ->
                        Timber.d("timber addDietData error $error")
                    }
                )
            } else {
                addDietDataUseCase.editWeight(
                    id,
                    it.toFloat(),
                    onSuccess = {
                        dialogDismiss()
                    },
                    onError = { error ->
                        Timber.d("timber addDietData error $error")
                    }
                )
            }
        }
    }

    override fun destroyedBottomDialog() {
        addDietDataUseCase.clearDisposable()
    }
}