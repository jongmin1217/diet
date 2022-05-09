package com.bellminp.diet.data.data_source.local

import androidx.room.Insert
import androidx.room.Update
import com.bellminp.diet.data.database.dao.DietDataDao
import com.bellminp.diet.data.database.dao.ProfileDao
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.ProfileData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProfileLocalDataSource @Inject constructor(
    private val local: ProfileDao
){
    fun getProfile(): Observable<List<ProfileData>>{
        return local.getProfile()
    }

    fun addProfile(data : ProfileData) : Completable{
        return local.addProfile(data)
    }

    fun updateProfile(data: ProfileData) : Completable{
        return local.updateProfile(data)
    }
}