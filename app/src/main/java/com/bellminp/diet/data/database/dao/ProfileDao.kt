package com.bellminp.diet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.ProfileData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_data")
    fun getProfile(): Observable<List<ProfileData>>

    @Insert
    fun addProfile(data : ProfileData) : Completable

    @Update
    fun updateProfile(data: ProfileData) : Completable
}