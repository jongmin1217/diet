package com.bellminp.diet.common.shared

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceCtrl {

    private lateinit var preference: SharedPreferences

    fun init(pContext: Context) {
        preference = pContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

}