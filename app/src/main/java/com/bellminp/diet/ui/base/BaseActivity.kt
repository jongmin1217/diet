package com.bellminp.diet.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bellminp.diet.R
import com.bellminp.diet.ui.write_type.WriteTypeActivity
import com.bellminp.diet.utils.Constants
import timber.log.Timber

abstract class BaseActivity <B : ViewDataBinding, VM : BaseViewModel>(@LayoutRes val layoutId: Int) : AppCompatActivity() {

    abstract val viewModel: VM
    lateinit var binding: B

    lateinit var progressDialog: Dialog
    lateinit var frameAnimation: AnimationDrawable
    lateinit var ivLoading: ImageView

    abstract fun initBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initBinding()
        initViewModelObserve()
        initValue()
    }

    private fun initValue(){
        progressDialog = AppCompatDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.dialog_loading)

        ivLoading = progressDialog.findViewById(R.id.ivLoading) as ImageView
        frameAnimation = ivLoading.background as AnimationDrawable
    }

    open fun initViewModelObserve(){
        with(viewModel){
            layoutClick.observe(this@BaseActivity,{
                layoutClickEvent()
            })

            showToast.observe(this@BaseActivity, { text->
                shortShowToast(text)
            })

            showLoading.observe(this@BaseActivity, { value->
                if(value) showProgressDialog()
                else hideProgressDialog()
            })

            finishView.observe(this@BaseActivity, {
                finish()
            })


        }
    }

    private fun showProgressDialog() {
        if(!progressDialog.isShowing){
            progressDialog.show()
            ivLoading.post {
                frameAnimation.start()
            }
        }
    }

    private fun hideProgressDialog() {
        ivLoading.run {
            if (progressDialog.isShowing){
                progressDialog.dismiss()
                progressDialog.cancel()
            }
            ivLoading.post {
                frameAnimation.stop()
            }
        }
    }

    @SuppressLint("ShowToast")
    private fun shortShowToast(value : String){
        Toast.makeText(this@BaseActivity, value, Toast.LENGTH_SHORT).show()
    }

    open fun layoutClickEvent(){
        closeKeyboard()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}