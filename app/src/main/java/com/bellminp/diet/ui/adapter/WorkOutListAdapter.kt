package com.bellminp.diet.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bellminp.diet.databinding.ItemIssueListBinding
import com.bellminp.diet.databinding.ItemWorkOutListBinding
import com.bellminp.diet.domain.model.DailyData
import com.bellminp.diet.domain.model.WorkOutData
import com.bellminp.diet.ui.write_type.WriteTypeViewModel

class WorkOutListAdapter(private val viewModel : WriteTypeViewModel) : RecyclerView.Adapter<WorkOutListAdapter.IssueHolder>(){

    var items = ArrayList<WorkOutData>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueHolder {
        val binding = ItemWorkOutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    override fun onBindViewHolder(holder: IssueHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class IssueHolder(val binding: ItemWorkOutListBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(workOutData: WorkOutData){
            binding.model = workOutData

            binding.layout.setOnClickListener {
                viewModel.workOutClick(adapterPosition)
            }

            binding.ivDelete.setOnClickListener {
                viewModel.workOutDeleteClick(adapterPosition)
            }
        }
    }
}