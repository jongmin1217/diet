package com.bellminp.diet.domain.usecase

import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.repository.ProfileRepository
import com.bellminp.diet.domain.usecase.base.UseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: ProfileRepository) : UseCase(){

    fun observableProfile(
        onSuccess: ((t: List<ProfileData>) -> Unit),
        onError: ((t: Throwable) -> Unit),
        onFinished: () -> Unit = {}
    ){
        addDisposable(
            repository.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(onFinished)
                .subscribe(onSuccess, onError)
        )
    }
}