package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetWeightUseCase @Inject constructor(private val repository: DietDataRepository) : UseCase() {
    fun observableLastWeight(
        onSuccess: ((t: List<Float>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getLastWeight()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }
}