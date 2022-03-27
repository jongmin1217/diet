package com.bellminp.diet.common.shared

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceCtrl {

    companion object{
        const val SP_NICKNAME = "SP_NICKNAME"
        const val SP_BIRTH = "SP_BIRTH"
        const val SP_HEIGHT = "SP_HEIGHT"
        const val SP_GENDER = "SP_GENDER"
        const val SP_PROFILE_IMAGE = "SP_PROFILE_IMAGE"
    }

    private lateinit var preference: SharedPreferences

    fun init(pContext: Context) {
        preference = pContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    private fun setStrSaveData(key: String, value: String) {
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun setIntSaveData(key: String, value: Int) {
        val editor = preference.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getStrLoadData(key: String): String? {
        return preference.getString(key, null)
    }


    private fun getIntLoadData(key: String): Int {
        return preference.getInt(key, -1)
    }

    fun getNickname() : String?{
        return getStrLoadData(SP_NICKNAME)
    }

    fun setNickname(value : String){
        setStrSaveData(SP_NICKNAME,value)
    }

    fun getProfileImg() : String?{
        return getStrLoadData(SP_PROFILE_IMAGE)
    }

}