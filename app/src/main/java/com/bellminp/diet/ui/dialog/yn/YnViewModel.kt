package com.bellminp.diet.ui.dialog.yn

import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.usecase.AddDietDataUseCase
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.DeleteDietData
import com.bellminp.diet.ui.data.DialogData
import com.bellminp.diet.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class YnViewModel @Inject constructor(
    private val addDietDataUseCase: AddDietDataUseCase
) : BaseViewModel() {

    fun okClick(data : DialogData){
        data.deleteData?.let {
            when(it.type){
                Constants.WEIGHT -> deleteWeight(it.id)
                Constants.CONTENT -> deleteContent(it.id)
                Constants.GOOD_LIST -> {
                    it.list?.let { list ->
                        it.position?.let { position ->
                            list.removeAt(position)
                            deleteGoodList(it.id,if(list.size == 0) null else list)
                        }
                    }
                }
                Constants.BAD_LIST -> {
                    it.list?.let { list ->
                        it.position?.let { position ->
                            list.removeAt(position)
                            deleteBadList(it.id,if(list.size == 0) null else list)
                        }
                    }
                }
                Constants.BODY -> deleteBody(it.id)
                else -> return
            }
        }
    }

    private fun deleteBody(id : Long){
        addDietDataUseCase.editBody(
            id,
            null,
            onError = { error ->
                Timber.d("timber addDietData error $error")
            }
        )
    }

    private fun deleteGoodList(id : Long, list : ArrayList<DailyData>?){
        addDietDataUseCase.editGoodList(
            id,
            list,
            onError = { error ->
                Timber.d("timber addDietData error $error")
            }
        )
    }

    private fun deleteBadList(id : Long, list : ArrayList<DailyData>?){
        addDietDataUseCase.editBadList(
            id,
            list,
            onError = { error ->
                Timber.d("timber addDietData error $error")
            }
        )
    }


    private fun deleteWeight(id : Long){
        addDietDataUseCase.editWeight(
            id,
            null,
            onError = { error ->
                Timber.d("timber addDietData error $error")
            }
        )
    }

    private fun deleteContent(id : Long){
        addDietDataUseCase.editContent(
            id,
            null,
            onError = { error ->
                Timber.d("timber addDietData error $error")
            }
        )
    }
}