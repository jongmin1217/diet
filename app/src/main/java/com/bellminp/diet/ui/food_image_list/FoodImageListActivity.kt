package com.bellminp.diet.ui.food_image_list

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityFoodImageListBinding
import com.bellminp.diet.ui.adapter.FoodImageListAdapter
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.data.DialogData
import com.bellminp.diet.ui.dialog.add_issue.BottomAddIssueDialog
import com.bellminp.diet.ui.dialog.work_out.BottomWorkOutDialog
import com.bellminp.diet.ui.dialog.yn.YnDialog
import com.bellminp.diet.ui.dialog.yn.YnViewModel
import com.bellminp.diet.ui.food_image.FoodImageActivity
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.ui.write_type.WriteTypeViewModel
import com.bellminp.diet.utils.BindAdapter
import com.bellminp.diet.utils.Constants
import com.esafirm.imagepicker.features.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class FoodImageListActivity : BaseActivity<ActivityFoodImageListBinding,FoodImageListViewModel>(R.layout.activity_food_image_list) {

    override val viewModel by viewModels<FoodImageListViewModel>()
    private val topViewModel by viewModels<TopViewModel>()

    lateinit var adapter : FoodImageListAdapter

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()


        topViewModel.btnBackVisible.value = true
        topViewModel.titleText.value = resources.getString(R.string.food_picture)

        viewModel.getFoodImage(false)

        initListener()
    }

    private fun initAdapter(){
        adapter = FoodImageListAdapter(viewModel)
        BindAdapter.foodImageListAdapter(binding.recyclerviewFoodList,adapter)
    }

    private fun initListener(){
        binding.recyclerviewFoodList.setOnScrollChangeListener { _, _, _, _, _ ->
            val layoutManager = binding.recyclerviewFoodList.layoutManager as LinearLayoutManager
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            val size = adapter.itemCount

            if(lastPosition > size-4 && !viewModel.loading){
                viewModel.getFoodImage(true)
            }
        }


    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(topViewModel) {
            backClick.observe(binding.lifecycleOwner!!, {
                finish()
            })
        }

        with(viewModel){
            foodImageList.observe(binding.lifecycleOwner!!,{ data ->
                if(!data.paging) adapter.items = data.list
                else adapter.addItems(data.list)
            })
        }
    }
}