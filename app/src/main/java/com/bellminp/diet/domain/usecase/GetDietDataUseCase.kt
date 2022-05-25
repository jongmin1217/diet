package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.Single
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

    fun singleFoodImage(
        regDate : Long,
        onSuccess: ((t: List<DietData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onStart: () -> Unit = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getFoodImage(regDate)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {onStart()}
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun allFoodImage(
        onSuccess: ((t: List<DietData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getAllFoodImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun singleBodyImage(
        regDate : Long,
        onSuccess: ((t: List<DietData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onStart: () -> Unit = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getBodyImage(regDate)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {onStart()}
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun allBodyImage(
        onSuccess: ((t: List<DietData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getAllBodyImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun getBodyImageRange(
        startDate : Long,
        endDate : Long,
        onSuccess: ((t: List<DietData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getBodyImageRange(startDate,endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }


}