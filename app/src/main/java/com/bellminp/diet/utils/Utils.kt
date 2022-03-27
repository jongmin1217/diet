package com.bellminp.diet.utils

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
import com.bellminp.diet.BuildConfig
import com.bellminp.diet.di.DietApplication
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class Utils {
    companion object{
        fun getYear(): Int = Calendar.getInstance().get(Calendar.YEAR)

        fun getMonth(): Int = Calendar.getInstance().get(Calendar.MONTH) + 1

        fun getDay(): Int = Calendar.getInstance().get(Calendar.DATE)

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
    }
}