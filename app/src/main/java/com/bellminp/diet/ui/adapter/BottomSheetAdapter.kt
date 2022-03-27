package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.common.listener.BottomBasicListener
import com.bellminp.diet.databinding.ItemBottomBasicBinding
import com.bellminp.diet.ui.base.BaseViewModel
import com.bellminp.diet.ui.data.BottomSheetData

class BottomSheetAdapter(private val listener: BottomBasicListener) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>(){
    var items = ArrayList<BottomSheetData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBottomBasicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: ItemBottomBasicBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(bottomSheetData: BottomSheetData){

            binding.model = bottomSheetData

            binding.layout.setOnClickListener {
                listener.bottomSheetClick(bottomSheetData)
            }
        }
    }
}