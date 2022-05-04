package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.databinding.ItemCalendarBinding
import com.bellminp.diet.databinding.ItemIssueListBinding
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.ui.data.CalendarData
import com.bellminp.diet.ui.main.fragment.CalendarViewModel
import com.bellminp.diet.ui.write_type.WriteTypeViewModel

class IssueListAdapter (private val viewModel : WriteTypeViewModel, private val type : Int) : RecyclerView.Adapter<IssueListAdapter.CalendarHolder>(){

    var items = ArrayList<DailyData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
        val binding = ItemIssueListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CalendarHolder(val binding: ItemIssueListBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(dailyData: DailyData){
            binding.model = dailyData

            binding.tvText.setOnClickListener {
                viewModel.issueListClick(type,adapterPosition)
            }

            binding.ivDelete.setOnClickListener {
                viewModel.issueDeleteClick(type,adapterPosition)
            }
        }
    }
}