package com.bellminp.diet.data.data_source.local

import com.bellminp.diet.data.database.dao.DietDataDao
import javax.inject.Inject

class DietDataLocalDataSource @Inject constructor(
        private val local : DietDataDao
) {

}