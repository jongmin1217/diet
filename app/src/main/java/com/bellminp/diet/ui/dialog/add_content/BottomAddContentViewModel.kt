package com.bellminp.diet.ui.dialog.add_content

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DateData
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BottomAddContentViewModel @Inject constructor(
    private val addDietDataUseCase: AddDietDataUseCase
)  : BaseViewModel() {

    val content = MutableLiveData<String>().default(String())

    fun addContent(id: Long?, date: DateData) {
        content.value?.let {
            if (id == null) {
                addDietDataUseCase.addDietData(
                    DietData(0, date.year, date.month, date.day, content = it),
                    onSuccess = {
                        dialogDismiss()
                    },
                    onError = { error ->
                        Timber.d("timber addDietData error $error")
                    }
                )
            } else {
                addDietDataUseCase.editContent(
                    id,
                    it,
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