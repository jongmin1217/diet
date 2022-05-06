package com.bellminp.diet.ui.dialog.food_name

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.LayoutBottomFoodNameBinding
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.ui.base.BaseBottomSheetDialog
import com.bellminp.diet.ui.data.DateData
import com.bellminp.diet.ui.dialog.photo.BottomPhotoViewModel
import timber.log.Timber

class BottomFoodNameDialog(
    private val initUri: Uri?,
    private val list : ArrayList<FoodData>?,
    private val position : Int?,
    private val id : Long?,
    private val dateData : DateData,
    private val loading: (() -> Unit)={},
    private val changeText : ((text : String) -> Unit)={}
) : BaseBottomSheetDialog<LayoutBottomFoodNameBinding, BottomFoodNameViewModel>(R.layout.layout_bottom_food_name) {
    override val viewModel by activityViewModels<BottomFoodNameViewModel>()

    var edit = false

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photoUri.value = initUri

        viewModel.name.value =if(position == null) ""
        else{
            if(list == null) ""
            else{
                edit = true
                viewModel.photoUrl.value = list[position].image
                list[position].type
            }
        }

        binding.editFoodName.run {
            binding.editFoodName.isFocusableInTouchMode = true
            binding.editFoodName.requestFocus()
            postDelayed({
                binding.editFoodName.setSelection(binding.editFoodName.text!!.length)
            }, 100)
        }

        initListener()
    }


    private fun initListener(){
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvOk.setOnClickListener {
            if(!edit) loading()
            else changeText(viewModel.name.value?:"")
            dismiss()
            viewModel.addFood(id,dateData,list, position)
        }
    }

}