package com.bellminp.diet.data.repository

import com.bellminp.diet.data.data_source.local.DietDataLocalDataSource
import com.bellminp.diet.data.data_source.local.ProfileLocalDataSource
import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.domain.repository.DietDataRepository
import com.bellminp.diet.domain.repository.ProfileRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dataSource: ProfileLocalDataSource
) : ProfileRepository {

    override fun getProfile(): Observable<List<ProfileData>> {
        return dataSource.getProfile()
    }

    override fun addProfile(data : ProfileData) : Completable {
        return dataSource.addProfile(data)
    }

    override fun updateProfile(data: ProfileData) : Completable {
        return dataSource.updateProfile(data)
    }
}