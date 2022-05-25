package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.databinding.ItemCalendarBinding
import com.bellminp.diet.databinding.ItemFoodImageBinding
import com.bellminp.diet.domain.model.FoodData
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.main.fragment.CalendarViewModel

class FoodImageAdapter (private val viewModel : BaseViewModel) : RecyclerView.Adapter<FoodImageAdapter.FoodImageHolder>(){

    var items = ArrayList<FoodData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodImageHolder {
        val binding = ItemFoodImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodImageHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun onBindViewHolder(holder: FoodImageHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class FoodImageHolder(val binding: ItemFoodImageBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(foodData: FoodData){
            binding.model = foodData

            binding.layout.setOnClickListener {
                viewModel.foodImageClick(adapterPosition,foodData)
            }
        }
    }
}