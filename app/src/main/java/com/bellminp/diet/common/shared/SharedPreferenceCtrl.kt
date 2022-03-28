package com.bellminp.diet.common.shared

import android.content.Context
import android.content.SharedPreferences
import com.bellminp.diet.utils.Utils

class SharedPreferenceCtrl {

    companion object{
        const val SP_NICKNAME = "SP_NICKNAME"
        const val SP_BIRTH = "SP_BIRTH"
        const val SP_HEIGHT = "SP_HEIGHT"
        const val SP_GENDER = "SP_GENDER"
        const val SP_TYPE = "SP_TYPE"
        const val SP_INIT_WEIGHT = "SP_INIT_WEIGHT"
        const val SP_GOAL_WEIGHT = "SP_GOAL_WEIGHT"
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

    private fun setFloatSaveData(key: String, value: Float) {
        val editor = preference.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    private fun getStrLoadData(key: String): String? {
        return preference.getString(key, null)
    }


    private fun getIntLoadData(key: String): Int {
        return preference.getInt(key, -1)
    }

    private fun getFloatLoadData(key: String): Float {
        return preference.getFloat(key, -1F)
    }

    fun getNickname() : String?{
        return getStrLoadData(SP_NICKNAME)
    }

    fun getBirth() : String?{
        return getStrLoadData(SP_BIRTH)
    }

    fun getGender() : Int{
        return getIntLoadData(SP_GENDER)
    }

    fun getType() : Int{
        return getIntLoadData(SP_TYPE)
    }

    fun getHeight() : Float{
        return getFloatLoadData(SP_HEIGHT)
    }

    fun getInitWeight() : Float{
        return getFloatLoadData(SP_INIT_WEIGHT)
    }

    fun getGoalWeight() : Float{
        return getFloatLoadData(SP_GOAL_WEIGHT)
    }

    fun getAge() : Int?{
        if(getBirth() == null) return null
        else{
            getBirth()?.let {
                val year = it.split(".")[0].toInt()
                val nowYear = Utils.getYear()
                return (nowYear - year) + 1
            }
            return null
        }
    }

    fun setNickname(value : String){
        setStrSaveData(SP_NICKNAME,value)
    }

    fun setBirth(value : String){
        setStrSaveData(SP_BIRTH,value)
    }

    fun setGender(value : Int){
        setIntSaveData(SP_GENDER,value)
    }

    fun setType(value : Int){
        setIntSaveData(SP_TYPE,value)
    }

    fun setHeight(value : Float){
        setFloatSaveData(SP_HEIGHT,value)
    }

    fun setInitWeight(value : Float){
        setFloatSaveData(SP_INIT_WEIGHT,value)
    }

    fun setGoalWeight(value : Float){
        setFloatSaveData(SP_GOAL_WEIGHT,value)
    }


}