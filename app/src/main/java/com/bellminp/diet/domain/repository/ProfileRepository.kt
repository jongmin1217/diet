package com.bellminp.diet.domain.repository

import com.bellminp.diet.domain.model.ProfileData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ProfileRepository {
    fun getProfile(): Observable<List<ProfileData>>
    fun addProfile(data : ProfileData) : Completable
    fun updateProfile(data: ProfileData) : Completable
}