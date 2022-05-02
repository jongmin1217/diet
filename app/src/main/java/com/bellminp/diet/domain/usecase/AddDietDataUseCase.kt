package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddDietDataUseCase @Inject constructor(private val repository: DietDataRepository) : UseCase() {

    fun addDietData(
        data : DietData,
        onSuccess: ((t: Long) -> Unit) = {},
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.addDietData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editWeight(
        id : Long,
        weight : Float,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editWeight(id,weight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editContent(
        id : Long,
        content : String,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editContent(id,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }
}