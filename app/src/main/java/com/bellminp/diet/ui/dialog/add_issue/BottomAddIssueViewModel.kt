package com.bellminp.diet.ui.dialog.add_issue

import androidx.lifecycle.MutableLiveData
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BottomAddIssueViewModel @Inject constructor(
    private val addDietDataUseCase: AddDietDataUseCase
) : BaseViewModel() {

    val issueText = MutableLiveData<String>().default(String())

    fun addIssue(id: Long?, date: DateData, type : Int, list : ArrayList<DailyData>?, position : Int?) {
        issueText.value?.let {
            val issueId = if(list == null || list.size == 0) 0 else list[list.size-1].id + 1
            val issueList = ArrayList<DailyData>()

            if(position == null){
                if(list != null) issueList.addAll(list)
                issueList.add(DailyData(issueId,it))
            }else{
                list?.let { list ->
                    list[position] = DailyData(list[position].id,it)
                    issueList.addAll(list)
                }
            }

            if(type == Constants.GOOD_LIST) addGood(id, date, issueList)
            else addBad(id, date, issueList)
        }
    }

    private fun addGood(id: Long?, date: DateData, list : ArrayList<DailyData>){
        if (id == null) {
            addDietDataUseCase.addDietData(
                DietData(0, date.year, date.month, date.day, goodList = list),
                onSuccess = {
                    dialogDismiss()
                },
                onError = { error ->
                    Timber.d("timber addDietData error $error")
                }
            )
        } else {
            addDietDataUseCase.editGoodList(
                id,
                list,
                onSuccess = {
                    dialogDismiss()
                },
                onError = { error ->
                    Timber.d("timber addDietData error $error")
                }
            )
        }
    }

    private fun addBad(id: Long?, date: DateData, list : ArrayList<DailyData>){
        if (id == null) {
            addDietDataUseCase.addDietData(
                DietData(0, date.year, date.month, date.day, badList = list),
                onSuccess = {
                    dialogDismiss()
                },
                onError = { error ->
                    Timber.d("timber addDietData error $error")
                }
            )
        } else {
            addDietDataUseCase.editBadList(
                id,
                list,
                onSuccess = {
                    dialogDismiss()
                },
                onError = { error ->
                    Timber.d("timber addDietData error $error")
                }
            )
        }
    }

    override fun destroyedBottomDialog() {
        addDietDataUseCase.clearDisposable()
    }
}