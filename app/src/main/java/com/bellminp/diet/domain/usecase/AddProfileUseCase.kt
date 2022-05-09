package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.repository.ProfileRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddProfileUseCase @Inject constructor(private val repository: ProfileRepository) : UseCase(){

    fun addProfile(
        data : ProfileData,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.addProfile(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }

    fun editProfile(
        data : ProfileData,
        onSuccess: (() -> Unit) = {},
        onError: ((t: Throwable) -> Unit) = {},
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.updateProfile(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }
}