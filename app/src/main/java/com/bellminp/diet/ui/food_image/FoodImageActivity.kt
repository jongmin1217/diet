package com.bellminp.diet.ui.food_image

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager.widget.ViewPager
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityFoodImageBinding
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.ui.adapter.ImageSlideAdapter
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.data.DeleteDietData
import com.bellminp.diet.ui.data.DialogData
import com.bellminp.diet.ui.data.FoodImageData
import com.bellminp.diet.ui.dialog.food_name.BottomFoodNameDialog
import com.bellminp.diet.ui.dialog.yn.YnDialog
import com.bellminp.diet.ui.dialog.yn.YnViewModel
import com.bellminp.diet.ui.intro.IntroViewModel
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FoodImageActivity :
    BaseActivity<ActivityFoodImageBinding, FoodImageViewModel>(R.layout.activity_food_image) {
    override val viewModel by viewModels<FoodImageViewModel>()
    private val topViewModel by viewModels<TopViewModel>()
    private val ynViewModel by viewModels<YnViewModel>()

    var imageSlideAdapter : ImageSlideAdapter? = null
    var data: FoodImageData? = null
    var selectPosition = 0

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getSerializableExtra(Constants.DATA) as FoodImageData

        data?.let {
            imageSlideAdapter = ImageSlideAdapter(this, binding.lifecycleOwner!!, it.list,{
                topViewModel.visible.value = topViewModel.visible.value != true
            })
            val dpValue = 20
            val d = resources.displayMetrics.density
            val margin = (dpValue * d).toInt()

            binding.viewPager.clipToPadding = false
            binding.viewPager.setPadding(margin, 0, margin, 0)
            binding.viewPager.pageMargin = margin / 2

            binding.viewPager.adapter = imageSlideAdapter
            binding.viewPager.currentItem = it.position

            selectPosition = it.position

            with(topViewModel) {
                btnBackVisible.value = true
                if(it.id != -1L){
                    btnDeleteVisible.value = true
                    btnEditVisible.value = true
                }
                titleText.value = it.list[it.position].type
            }
        }

        initListener()
    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()
        with(topViewModel) {
            backClick.observe(binding.lifecycleOwner!!, {
                finish()
            })

            editClick.observe(binding.lifecycleOwner!!, {
                data?.let {
                    BottomFoodNameDialog(
                        null,
                        it.list,
                        selectPosition,
                        it.id,
                        DateData(0, 0, 0),
                        changeText = { text ->
                            topViewModel.titleText.value = text
                        }
                    ).show(supportFragmentManager, "food_name")
                }
            })

            deleteClick.observe(binding.lifecycleOwner!!, {
                data?.let { data ->
                    YnDialog(
                        this@FoodImageActivity,
                        binding.lifecycleOwner!!,
                        ynViewModel,
                        DialogData(
                            deleteData = DeleteDietData(
                                data.id,
                                Constants.FOOD,
                                position = selectPosition,
                                foodList = data.list
                            )
                        ),
                        delete = {
                            imageSlideAdapter?.notifyDataSetChanged()

                            if(imageSlideAdapter?.count?:0 == 0) finish()
                            else if(it != -1) {
                                topViewModel.titleText.value = if(it >= data.list.size) data.list[data.list.size-1].type
                                else data.list[it].type
                            }

                        }
                    ).show()
                }
            })
        }
    }

    private fun initListener() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            @SuppressLint("MissingSuperCall")
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                data?.let {
                    topViewModel.titleText.value = it.list[position].type
                    selectPosition = position
                }
            }
        })
    }

}