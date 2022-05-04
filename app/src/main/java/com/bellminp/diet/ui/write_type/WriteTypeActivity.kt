package com.bellminp.diet.ui.write_type

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bellminp.diet.BuildConfig
import com.bellminp.diet.R
import com.bellminp.diet.databinding.ActivityWriteTypeBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.ui.adapter.IssueListAdapter
import com.bellminp.diet.ui.base.BaseActivity
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.data.DeleteDietData
import com.bellminp.diet.ui.data.DialogData
import com.bellminp.diet.ui.dialog.add_content.BottomAddContentDialog
import com.bellminp.diet.ui.dialog.add_issue.BottomAddIssueDialog
import com.bellminp.diet.ui.dialog.content_detail.BottomContentDetailDialog
import com.bellminp.diet.ui.dialog.image_detail.ImageDetailDialog
import com.bellminp.diet.ui.dialog.weight.BottomWeightDialog
import com.bellminp.diet.ui.dialog.yn.YnDialog
import com.bellminp.diet.ui.dialog.yn.YnViewModel
import com.bellminp.diet.ui.top.TopViewModel
import com.bellminp.diet.utils.BindAdapter
import com.bellminp.diet.utils.Constants
import com.bellminp.diet.utils.Utils
import com.esafirm.imagepicker.features.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.concurrent.Executor

@AndroidEntryPoint
class WriteTypeActivity :
    BaseActivity<ActivityWriteTypeBinding, WriteTypeViewModel>(R.layout.activity_write_type) {
    override val viewModel by viewModels<WriteTypeViewModel>()
    private val topViewModel by viewModels<TopViewModel>()
    private val ynViewModel by viewModels<YnViewModel>()

    var currentPhotoPath = ""

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.d("timber ${ImagePicker.getImages(result.data)[0].uri}")
                //viewModel.profileImg.value = ImagePicker.getImages(result.data)[0].uri.toString()
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.saveBodyImage(currentPhotoPath)
            }
        }

    override fun initBinding() {
        binding.vm = viewModel
        binding.topViewModel = topViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (intent.hasExtra(Constants.DATA)) {
            val date = intent.getSerializableExtra(Constants.DATA) as DateData
            viewModel.getDietData(date)
            topViewModel.btnBackVisible.value = true
            topViewModel.titleText.value = String.format(
                "%d.%s.%s",
                date.year,
                Utils.dateText(date.month),
                Utils.dateText(date.day)
            )
        }

        initAdapter()
        initListener()
    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(topViewModel) {
            backClick.observe(binding.lifecycleOwner!!, {
                finish()
            })
        }

        with(viewModel) {
            editIssue.observe(binding.lifecycleOwner!!, { data ->
                date?.let { date ->
                    BottomAddIssueDialog(
                        data[0],
                        if (data[0] == Constants.GOOD_LIST) dietData.value?.goodList else dietData.value?.badList,
                        data[1],
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "issue")
                }
            })

            deleteIssue.observe(binding.lifecycleOwner!!, { data ->
                YnDialog(
                    this@WriteTypeActivity,
                    binding.lifecycleOwner!!,
                    ynViewModel,
                    DialogData(deleteData = data)
                ).show()
            })
        }
    }

    private fun initAdapter() {
        BindAdapter.issueListAdapter(
            binding.recyclerviewGoodList,
            IssueListAdapter(viewModel, Constants.GOOD_LIST)
        )
        BindAdapter.issueListAdapter(
            binding.recyclerviewBadList,
            IssueListAdapter(viewModel, Constants.BAD_LIST)
        )
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun initListener() {
        binding.btnWeightAdd.setOnClickListener {
            with(viewModel) {
                date?.let { date ->
                    BottomWeightDialog(
                        dietData.value?.weight,
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "weight")
                }
            }
        }

        binding.btnWeightDelete.setOnClickListener {
            viewModel.dietData.value?.id?.let { id ->
                YnDialog(
                    this,
                    binding.lifecycleOwner!!,
                    ynViewModel,
                    DialogData(deleteData = DeleteDietData(id, Constants.WEIGHT))
                ).show()
            }
        }

        binding.btnContentAdd.setOnClickListener {
            with(viewModel) {
                date?.let { date ->
                    BottomAddContentDialog(
                        dietData.value?.content,
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "content")
                }
            }
        }

        binding.btnContentDelete.setOnClickListener {
            viewModel.dietData.value?.id?.let { id ->
                YnDialog(
                    this,
                    binding.lifecycleOwner!!,
                    ynViewModel,
                    DialogData(deleteData = DeleteDietData(id, Constants.CONTENT))
                ).show()
            }
        }

        binding.tvContentValue.setOnClickListener {
            viewModel.dietData.value?.content?.let { text ->
                BottomContentDetailDialog(text).show(supportFragmentManager, "content_detail")
            }
        }

        binding.btnGoodAdd.setOnClickListener {
            with(viewModel) {
                date?.let { date ->
                    BottomAddIssueDialog(
                        Constants.GOOD_LIST,
                        dietData.value?.goodList,
                        null,
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "issue")
                }
            }
        }

        binding.btnBadAdd.setOnClickListener {
            with(viewModel) {
                date?.let { date ->
                    BottomAddIssueDialog(
                        Constants.BAD_LIST,
                        dietData.value?.badList,
                        null,
                        dietData.value?.id,
                        date
                    ).show(supportFragmentManager, "issue")
                }
            }
        }

        binding.btnBodyAdd.setOnClickListener {
            val cameraPermissionCheck = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            )
            if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1000
                )
            } else {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                    takePictureIntent.resolveActivity(packageManager)?.also {
                        val path = Utils.createBodyPicture()
                        path?.let {
                            if (it.size == 2 && it[1] is File) {
                                currentPhotoPath = it[0] as String
                                val photoUri = FileProvider.getUriForFile(
                                    this@WriteTypeActivity,
                                    "${packageName}.fileprovider",
                                    it[1] as File
                                )
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                                cameraLauncher.launch(takePictureIntent)
                            }

                        }

                    }
                }
            }
        }

        binding.ivBodyValue.setOnClickListener {
            viewModel.dietData.value?.body?.let {
                ImageDetailDialog(this, binding.lifecycleOwner!!, viewModel, it).show()
            }
        }

        binding.btnBodyDelete.setOnClickListener {
            viewModel.dietData.value?.id?.let { id ->
                YnDialog(
                    this,
                    binding.lifecycleOwner!!,
                    ynViewModel,
                    DialogData(deleteData = DeleteDietData(id, Constants.BODY))
                ).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    DietApplication.mInstance.resources.getString(R.string.camera_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        viewModel.clearDietDataObserve()
        super.onDestroy()
    }
}