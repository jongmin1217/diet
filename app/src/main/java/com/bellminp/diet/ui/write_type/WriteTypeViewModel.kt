package com.bellminp.diet.ui.write_type

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.domain.usecase.GetDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.data.DeleteDietData
import com.bellminp.diet.ui.data.FoodImageData
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.SingleLiveEvent
import com.bellminp.diet.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rx.Single
import rx.schedulers.Schedulers.io
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class WriteTypeViewModel @Inject constructor(
    private val getDietDataUseCase: GetDietDataUseCase,
    private val addDietDataUseCase: AddDietDataUseCase
) : BaseViewModel() {

    private val _editIssue = SingleLiveEvent<ArrayList<Int>>()
    val editIssue: LiveData<ArrayList<Int>> get() = _editIssue

    private val _deleteIssue = SingleLiveEvent<DeleteDietData>()
    val deleteIssue: LiveData<DeleteDietData> get() = _deleteIssue

    private val _goFoodDetail = SingleLiveEvent<FoodImageData>()
    val goFoodDetail: LiveData<FoodImageData> get() = _goFoodDetail

    var date: DateData? = null
    val dietData = MutableLiveData<DietData>().default(null)

    fun getDietData(date: DateData) {
        this.date = date
        getDietDataUseCase.observableDietData(date.year, date.month, date.day,
            onSuccess = {
                showLoading(false)
                dietData.value = it
            }, onError = {
                Timber.d("timber observableDietData $it")
            }
        )
    }


    fun saveBodyImage(path: String) {
        showLoading(true)
        viewModelScope.launch(Dispatchers.IO){
            val uri = Uri.fromFile(File(path))
            val savePath = Utils.saveBody(uri)
            addBody(savePath)
        }
    }

    private fun addBody(path: String?) {
        if (dietData.value == null) {
            date?.let {date ->
                addDietDataUseCase.addDietData(
                    DietData(0,date.year,date.month,date.day, body = path),
                    onFinished = {
                        showLoading(false)
                    }
                )
            }

        } else {
            addDietDataUseCase.editBody(dietData.value!!.id, path,
                onFinished = {
                    showLoading(false)
                }
            )
        }
    }


    fun issueListClick(type: Int, position: Int) {
        _editIssue.value = arrayListOf(type, position)
    }

    fun issueDeleteClick(type: Int, position: Int) {
        dietData.value?.let {
            _deleteIssue.value = DeleteDietData(
                it.id,
                type,
                list = if (type == Constants.GOOD_LIST) it.goodList else it.badList,
                position
            )
        }
    }

    override fun foodImageClick(position: Int){
        dietData.value?.let {
            it.food?.let { food->
                _goFoodDetail.value = FoodImageData(it.id,food,position)
            }
        }
    }

    fun clearDietDataObserve() {
        getDietDataUseCase.clearDisposable()
    }
}

