package com.bellminp.diet.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.bellminp.diet.BuildConfig
import com.bellminp.diet.di.DietApplication
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Utils {
    companion object{
        fun getYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

        fun getMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1

        fun getDay(): Int = Calendar.getInstance().get(Calendar.DATE)

        fun beforeYear(year : Int, month : Int) : Int{
            return if(month == 1) year -1 else year
        }

        fun beforeMonth(month : Int) : Int{
            return if(month == 1) 12 else month - 1
        }

        fun afterYear(year : Int, month : Int) : Int{
            return if(month == 12) year + 1 else year
        }

        fun afterMonth(month : Int) : Int{
            return if(month == 12) 1 else month + 1
        }

        fun dateText(value : Int) : String{
            return if (value < 10) "0$value" else value.toString()
        }

        fun saveProfile(uri : Uri){
            try {
                val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(DietApplication.mInstance.contentResolver,uri))
                }else{
                    MediaStore.Images.Media.getBitmap(DietApplication.mInstance.contentResolver,uri)
                }

                val previousFile =
                    File("data/data/" + BuildConfig.APPLICATION_ID + "/app_profile_image/profile.jpg")
                if (previousFile.exists()) previousFile.delete()

                val imgFileName = "profile.jpg"
                val cw = ContextWrapper(DietApplication.mInstance.applicationContext)
                val directory = cw.getDir("profile_image", Context.MODE_PRIVATE)
                val file = File(directory, imgFileName)

                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()

            }catch (e: IOException){
                e.printStackTrace()
            }
        }

        fun deleteProfile(){
            val previousFile =
                File("data/data/" + BuildConfig.APPLICATION_ID + "/app_profile_image/profile.jpg")
            if (previousFile.exists()) previousFile.delete()
        }

        fun createBodyPicture() : ArrayList<Any>?{
            return try {
                val returnString = ArrayList<Any>()
                val imgFileName = getUnixTime().toString()
                val directory = DietApplication.mInstance.cacheDir

                val tempImage = File.createTempFile(imgFileName,".jpg",directory)
                returnString.add(tempImage.absolutePath)
                returnString.add(tempImage)

                returnString

            }catch (e: IOException){
                e.printStackTrace()
                null
            }
        }

        fun saveBody(uri : Uri) : String?{
            return try {
                val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(DietApplication.mInstance.contentResolver,uri))
                }else{
                    MediaStore.Images.Media.getBitmap(DietApplication.mInstance.contentResolver,uri)
                }

                val imgFileName = "${getUnixTime()}.jpg"
                val cw = ContextWrapper(DietApplication.mInstance.applicationContext)
                val directory = cw.getDir("body_image", Context.MODE_PRIVATE)
                val file = File(directory, imgFileName)

                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()

                uri.path?.let {
                    val deleteFile = File(it)
                    if(deleteFile.exists()) deleteFile.delete()
                }

                "data/data/" + BuildConfig.APPLICATION_ID + "/app_body_image/$imgFileName"

            }catch (e: IOException){
                e.printStackTrace()
                null
            }
        }

        fun saveFood(uri : Uri) : String?{
            return try {
                val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(DietApplication.mInstance.contentResolver,uri))
                }else{
                    MediaStore.Images.Media.getBitmap(DietApplication.mInstance.contentResolver,uri)
                }

                val imgFileName = "${getUnixTime()}.jpg"
                val cw = ContextWrapper(DietApplication.mInstance.applicationContext)
                val directory = cw.getDir("food_image", Context.MODE_PRIVATE)
                val file = File(directory, imgFileName)

                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()

                uri.path?.let {
                    val deleteFile = File(it)
                    if(deleteFile.exists()) deleteFile.delete()
                }

                "data/data/" + BuildConfig.APPLICATION_ID + "/app_food_image/$imgFileName"

            }catch (e: IOException){
                e.printStackTrace()
                null
            }
        }

        fun getUnixTime(): Long {
            return System.currentTimeMillis()
        }

        fun lastDay(year : Int, month : Int) : Int{
            val cal = Calendar.getInstance()

            cal.set(year,month-1,1)

            return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        @SuppressLint("SimpleDateFormat")
        fun getDateDay(date: String, dateType: String): Int {
            val dateFormat = SimpleDateFormat(dateType)
            val nDate = dateFormat.parse(date)

            val cal = Calendar.getInstance()
            cal.time = nDate

            return cal.get(Calendar.DAY_OF_WEEK)
        }

        fun getDate(year : Int, month : Int, day : Int) : Int{
            return String.format("%d%s%s",year,if(month < 10) "0$month" else month,if(day < 10) "0$day" else day).toInt()
        }
    }
}