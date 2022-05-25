package com.bellminp.diet.ui.add_profile

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityAddProfileBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.ProfileData
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.data.BottomSheetData
import com.bellminp.diet.ui.dialog.basic.BottomBasicDialog
import com.bellminp.diet.ui.dialog.basic.BottomBasicViewModel
import com.bellminp.diet.ui.dialog.date.BottomDateDialog
import com.bellminp.diet.ui.dialog.date.BottomDateViewModel
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.Constants
import com.esafirm.imagepicker.features.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProfileActivity :
    BaseActivity<ActivityAddProfileBinding, AddProfileViewModel>(R.layout.activity_add_profile) {
    override val viewModel by viewModels<AddProfileViewModel>()
    private val topViewModel by viewModels<TopViewModel>()
    private val bottomViewModel by viewModels<BottomBasicViewModel>()
    private val dateViewModel by viewModels<BottomDateViewModel>()

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.profileImg.value = ImagePicker.getImages(result.data)[0].uri.toString()
            }
        }

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(Constants.TYPE)?.let {
            topViewModel.btnBackVisible.value = true
            topViewModel.titleText.value = if(it == Constants.ADD) DietApplication.mInstance.resources.getString(R.string.add_profile_title)
            else {
                if(intent.hasExtra(Constants.DATA)){
                    viewModel.initEditData(intent.getSerializableExtra(Constants.DATA) as ProfileData)
                }
                DietApplication.mInstance.resources.getString(R.string.profile_edit)
            }

            viewModel.edit.value = it == Constants.EDIT

            initListener()
        }
    }

    private fun initListener() {
        binding.ivProfileImg.setOnClickListener {
            if (viewModel.profileImg.value == null) selectImage()
            else {
                val items = arrayListOf(
                    BottomSheetData(
                        0, DietApplication.mInstance.resources.getString(R.string.edit),
                        Color.parseColor("#007AFF"), true, 0
                    ),
                    BottomSheetData(
                        1, DietApplication.mInstance.resources.getString(R.string.delete),
                        Color.parseColor("#FF2D55"), true, 0
                    )
                )
                BottomBasicDialog(items).show(supportFragmentManager, "imageSelect")
            }
        }

        binding.ivCalendar.setOnClickListener {
            BottomDateDialog(null,Constants.BIRTH).show(supportFragmentManager, "date")
        }

        binding.tvBirthValue.setOnClickListener {
            BottomDateDialog(viewModel.birth.value,Constants.BIRTH).show(supportFragmentManager, "date")
        }

        binding.ivStartWorkOut.setOnClickListener {
            BottomDateDialog(null,Constants.START_WORK_OUT).show(supportFragmentManager, "date")
        }

        binding.tvStartWorkOutValue.setOnClickListener {
            BottomDateDialog(viewModel.startWorkOut.value,Constants.START_WORK_OUT).show(supportFragmentManager, "date")
        }

        binding.ivEndWorkOut.setOnClickListener {
            BottomDateDialog(null,Constants.END_WORK_OUT).show(supportFragmentManager, "date")
        }

        binding.tvEndWorkOutValue.setOnClickListener {
            BottomDateDialog(viewModel.endWorkOut.value,Constants.END_WORK_OUT).show(supportFragmentManager, "date")
        }


    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(topViewModel) {
            backClick.observe(binding.lifecycleOwner!!, {
                finish()
            })
        }

        with(bottomViewModel) {
            bottomSheetData.observe(binding.lifecycleOwner!!, { data ->
                when(data.data){
                    0 ->{
                        if(data.id == 0) selectImage()
                        else viewModel.profileImg.value = null
                    }
                }
            })
        }

        with(dateViewModel){
            dateSelect.observe(binding.lifecycleOwner!!, { birth ->
                when(birth.type){
                    Constants.BIRTH -> viewModel.birth.value = String.format("%d.%d.%d",birth.year,birth.month,birth.day)
                    Constants.START_WORK_OUT -> viewModel.startWorkOut.value = String.format("%d.%d.%d",birth.year,birth.month,birth.day)
                    Constants.END_WORK_OUT -> viewModel.endWorkOut.value = String.format("%d.%d.%d",birth.year,birth.month,birth.day)
                }

            })
        }
    }

    private fun selectImage(){
        galleryLauncher.launch(
            ImagePicker.create(this).single().showCamera(false).folderMode(true)
                .toolbarFolderTitle(DietApplication.mInstance.resources.getString(R.string.folder))
                .toolbarDoneButtonText("선택")
                .theme(R.style.CustomImagePickerTheme).getIntent(this)
        )
    }
}