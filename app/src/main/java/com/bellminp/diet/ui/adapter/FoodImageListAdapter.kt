package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.databinding.ItemFoodImageListBinding
import com.bellminp.diet.databinding.ItemIssueListBinding
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.write_type.WriteTypeViewModel
import com.bellminp.diet.utils.BindAdapter

class FoodImageListAdapter(private val viewModel : BaseViewModel) : RecyclerView.Adapter<FoodImageListAdapter.ViewHolder>(){

    var items = ArrayList<DietData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodImageListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(value: ArrayList<DietData>) {
        items.addAll(value)
        notifyItemRangeInserted(itemCount - value.size, value.size)
    }


    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: ItemFoodImageListBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(dietData: DietData){
            binding.model = dietData

            val adapter = FoodImageAdapter(viewModel)
            BindAdapter.dateFoodAdapter(binding.recyclerviewDateFood, adapter)
            dietData.food?.let {
                adapter.items = it
            }
        }
    }
}