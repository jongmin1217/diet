package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.domain.model.WorkOutData
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddDietDataUseCase @Inject constructor(private val repository: DietDataRepository) : UseCase() {

    fun addDietData(
        data : DietData,
        onSuccess: ((t: Long) -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        data.settingDate()
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
        weight : Float?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
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
        content : String?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
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

    fun editGoodList(
        id : Long,
        goodList : ArrayList<DailyData>?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editGood(id,goodList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editBadList(
        id : Long,
        badList : ArrayList<DailyData>?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editBad(id,badList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editBody(
        id : Long,
        body : String?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editBody(id,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editFood(
        id : Long,
        food : ArrayList<FoodData>?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editFood(id,food)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editWorkOut(
        id : Long,
        workOut : ArrayList<WorkOutData>?,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.editWorkOut(id,workOut)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }
}