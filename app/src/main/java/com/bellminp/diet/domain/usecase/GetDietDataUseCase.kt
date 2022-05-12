package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetDietDataUseCase @Inject constructor(private val repository: DietDataRepository) : UseCase() {

    fun observableDietDataList(
        year : Int,
        month : Int,
        onSuccess: ((t: List<DietData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getMonth(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun observableDietData(
        year : Int,
        month : Int,
        day : Int,
        onSuccess: ((t: DietData?) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getDay(year, month,day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun observableWeight(
        year : Int,
        month : Int,
        onSuccess: ((t: List<Float>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getMonthWeight(year, month)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }


}