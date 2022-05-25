package com.bellminp.diet.ui.data

import dagger.multibindings.IntoMap

data class DateSelectData(
    val year : Int,
    val month : Int,
    val day : Int,
    val type : Int
)

data class MonthSelectData(
    val year : Int,
    val month : Int
)