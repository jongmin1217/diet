package com.bellminp.diet.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import timber.log.Timber

abstract class BaseActivity <B : ViewDataBinding, VM : BaseViewModel>(@LayoutRes val layoutId: Int) : AppCompatActivity() {

    abstract val viewModel: VM
    lateinit var binding: B

    abstract fun initBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        initBinding()
        initViewModelObserve()
    }

    open fun initViewModelObserve(){
        with(viewModel){
            layoutClick.observe(this@BaseActivity,{
                layoutClickEvent()
            })

            showToast.observe(this@BaseActivity, { text->
                shortShowToast(text)
            })
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