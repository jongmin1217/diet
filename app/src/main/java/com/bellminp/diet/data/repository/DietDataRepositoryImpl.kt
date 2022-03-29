package com.bellminp.diet.data.repository

import com.bellminp.diet.data.data_source.local.DietDataLocalDataSource
import com.bellminp.diet.domain.repository.DietDataRepository
import javax.inject.Inject

class DietDataRepositoryImpl @Inject constructor(
    private val dataSource: DietDataLocalDataSource
) : DietDataRepository{
}