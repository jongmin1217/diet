package com.bellminp.diet.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityMainBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.main.fragment.CalendarFragment
import com.bellminp.diet.ui.main.fragment.GraphFragment
import com.bellminp.diet.ui.main.fragment.ProfileFragment
import com.bellminp.diet.ui.main.fragment.SlideFragment
import com.bellminp.diet.ui.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>(R.layout.activity_main) {
    override val viewModel by viewModels<MainViewModel>()
    private val topViewModel by viewModels<TopViewModel>()

    private val calendarFragment = CalendarFragment()
    private var graphFragment : GraphFragment? = null
    private var slideFragment : SlideFragment? = null
    private var profileFragment : ProfileFragment? = null

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLayout()
        initListener()
    }

    private fun initLayout(){
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, calendarFragment).commit()
    }

    private fun initListener(){
        binding.bottomNavMain.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.bottom_nav_calendar -> {
                        topViewModel.titleText.value = DietApplication.mInstance.resources.getString(R.string.title_calendar)
                        changeFragment(calendarFragment)
                    }
                    R.id.bottom_nav_graph -> {
                        topViewModel.titleText.value = DietApplication.mInstance.resources.getString(R.string.title_graph)
                        if(graphFragment == null){
                            graphFragment = GraphFragment()
                            addFragment(graphFragment!!)
                        }else changeFragment(graphFragment!!)
                    }
                    R.id.bottom_nav_slide -> {
                        topViewModel.titleText.value = DietApplication.mInstance.resources.getString(R.string.title_slide)
                        if(slideFragment == null){
                            slideFragment = SlideFragment()
                            addFragment(slideFragment!!)
                        }else changeFragment(slideFragment!!)
                    }
                    R.id.bottom_nav_profile -> {
                        topViewModel.titleText.value = DietApplication.mInstance.resources.getString(R.string.title_profile)
                        if(profileFragment == null){
                            profileFragment = ProfileFragment()
                            addFragment(profileFragment!!)
                        }else changeFragment(profileFragment!!)
                    }
                }
                true
            }
            selectedItemId = R.id.bottom_nav_calendar
        }
    }

    private fun changeFragment(newFragment: Fragment) {
        if (!newFragment.isVisible) {
            hideFragment()
            supportFragmentManager.beginTransaction().show(newFragment).commit()
        }
    }

    private fun addFragment(newFragment: Fragment) {
        for (fragment: Fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                supportFragmentManager.beginTransaction().hide(fragment).commit()
                supportFragmentManager.beginTransaction().add(binding.frameLayout.id, newFragment).commit()
            }
        }
    }

    private fun hideFragment() {
        for (fragment: Fragment in supportFragmentManager.fragments) {
            if (fragment.isVisible) {
                supportFragmentManager.beginTransaction().hide(fragment).commit()
            }
        }
    }


}