package com.bellminp.diet.ui.body_image

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityBodyImageBinding
import com.bellminp.diet.ui.adapter.BodyImageAdapter
import com.bellminp.diet.ui.adapter.FoodImageListAdapter
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.food_image_list.FoodImageListViewModel
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.BindAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BodyImageActivity : BaseActivity<ActivityBodyImageBinding,BodyImageViewModel>(R.layout.activity_body_image) {
    override val viewModel by viewModels<BodyImageViewModel>()
    private val topViewModel by viewModels<TopViewModel>()

    lateinit var adapter : BodyImageAdapter

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(topViewModel){
            btnBackVisible.value = true
            titleText.value = resources.getString(R.string.body_picture)
        }

        initAdapter()
        initListener()
        viewModel.getBodyImage(false)
    }

    private fun initAdapter(){
        adapter = BodyImageAdapter {
            viewModel.getAllBody(it)
        }
        BindAdapter.bodyImageAdapter(binding.recyclerviewBodyImage,adapter)
    }

    private fun initListener(){
        binding.recyclerviewBodyImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = binding.recyclerviewBodyImage.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                val size = adapter.itemCount

                if(lastPosition > size-7 && !viewModel.loading){
                    viewModel.getBodyImage(true)
                }
            }
        })
    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(topViewModel) {
            backClick.observe(binding.lifecycleOwner!!, {
                finish()
            })
        }

        with(viewModel){
            bodyImageList.observe(binding.lifecycleOwner!!,{ data ->
                if(!data.paging) adapter.items = data.list
                else adapter.addItems(data.list)
            })
        }
    }
}