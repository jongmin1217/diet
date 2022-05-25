package com.bellminp.diet.ui.main.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentCalendarBinding
import com.bellminp.diet.databinding.FragmentGraphBinding
import com.bellminp.diet.di.DietApplication
import com.bellminp.diet.domain.model.DietData
import com.bellminp.diet.ui.base.BaseFragment
import com.bellminp.diet.ui.data.MonthSelectData
import com.bellminp.diet.ui.dialog.month.BottomMonthDialog
import com.bellminp.diet.utils.Utils
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.withSign

@AndroidEntryPoint
class GraphFragment(private val initDate : String) : BaseFragment<FragmentGraphBinding, GraphViewModel>(R.layout.fragment_graph) {
    override val viewModel by activityViewModels<GraphViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDate.text = initDate
        viewModel.initWeight(initDate.split(".")[0].toInt(),initDate.split(".")[1].toInt())
        viewModel.initWorkDate()
        initListener()
    }

    override fun initViewModelObserve() {
        super.initViewModelObserve()

        with(viewModel){
            initWeight.observe(viewLifecycleOwner,{
                initWeightChart(it)
                initWorkOutChart(it)
            })
        }
    }

    private fun initWeightChart(data : List<DietData>){
        val items = data.filter { it.weight != null }
        val list = items.map { it.weight!! }
        val minWeight = if(list.isEmpty()) 0f else{
            if(Collections.min(list) > 1f) Collections.min(list) - 1f else 0f
        }
        val maxWeight = if(list.isEmpty()) 0f else Collections.max(list) + 1f

        binding.tvMinWeightValue.text = if(minWeight == 0f) resources.getString(R.string.no_data) else String.format("%.1fkg",Collections.min(list))
        binding.tvMaxWeightValue.text = if(maxWeight == 0f) resources.getString(R.string.no_data) else String.format("%.1fkg",Collections.max(list))

        val selectYear = binding.btnDate.text.toString().split(".")[0].toInt()
        val selectMonth = binding.btnDate.text.toString().split(".")[1].toInt()
        val lastDay = if(selectYear == Utils.getYear() && selectMonth == Utils.getMonth()) Utils.getDay().toFloat()
        else Utils.lastDay(selectYear,selectMonth).toFloat()

        val xAxis = binding.chartWeight.xAxis

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 10f
            setDrawGridLines(false)
            granularity = 1f
            axisMinimum = 1f
            axisMaximum = lastDay
            isGranularityEnabled = true
        }
        binding.chartWeight.apply {
            axisRight.isEnabled = false
            axisLeft.axisMaximum = maxWeight
            axisLeft.axisMinimum = minWeight
            description.text = resources.getString(R.string.day)
            description.textSize = 12f
            setExtraOffsets(20f,20f,20f,0f)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            legend.apply {
                textSize = 12f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
        }

        val entryList = ArrayList<Entry>()
        for(i in 0..lastDay.toInt()){
            val index = data.indexOfFirst { it.day == i }
            if(index != -1){
                data[index].weight?.let {
                    entryList.add(Entry(i.toFloat(),it))
                }
            }
        }

        binding.chartWeight.data = LineData(weightSet(entryList))
        binding.chartWeight.invalidate()
    }

    private fun weightSet(data : List<Entry>) : LineDataSet{
        val set = LineDataSet(data,resources.getString(R.string.weight_title))
        set.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = resources.getColor(R.color.red,null)
            setCircleColor(resources.getColor(R.color.red,null))
            valueTextSize = 10f
            valueFormatter = WeightValueFormatter()
            lineWidth = 1.5f
            circleRadius = 3f
            fillAlpha = 0
            fillColor = resources.getColor(R.color.red,null)
            isHighlightEnabled = false
            setDrawValues(true)
            return this
        }
    }

    private fun initWorkOutChart(data : List<DietData>){
        val items = data.filter { it.totalWorkOutTime() != null }
        val list = items.map { it.totalWorkOutTime()!! }
        val maxTime = if(list.isEmpty()) 0f else Collections.max(list) + 10f

        val selectYear = binding.btnDate.text.toString().split(".")[0].toInt()
        val selectMonth = binding.btnDate.text.toString().split(".")[1].toInt()
        val lastDay = if(selectYear == Utils.getYear() && selectMonth == Utils.getMonth()) Utils.getDay().toFloat()
        else Utils.lastDay(selectYear,selectMonth).toFloat()

        val xAxis = binding.chartWorkOut.xAxis

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 10f
            setDrawGridLines(false)
            granularity = 1f
            axisMinimum = 1f
            axisMaximum = lastDay
            isGranularityEnabled = true
        }
        binding.chartWorkOut.apply {
            axisRight.isEnabled = false
            axisLeft.axisMaximum = maxTime
            axisLeft.axisMinimum = 0f
            description.text = resources.getString(R.string.day)
            description.textSize = 12f
            setExtraOffsets(20f,20f,20f,0f)
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            legend.apply {
                textSize = 12f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
        }

        var cnt = 0
        var totalWorkoutTime = 0f
        val entryList = ArrayList<Entry>()
        for(i in 0..lastDay.toInt()){
            val index = data.indexOfFirst { it.day == i }
            if(index != -1){
                if(data[index].totalWorkOutTime() == null) entryList.add(Entry(i.toFloat(),0f))
                else{
                    if(data[index].totalWorkOutTime()!!.toFloat() >= 60) cnt++
                    totalWorkoutTime += data[index].totalWorkOutTime()!!.toFloat()
                    entryList.add(Entry(i.toFloat(),data[index].totalWorkOutTime()!!.toFloat()))
                }
            }else{
                entryList.add(Entry(i.toFloat(),0f))
            }
        }

        binding.tvWorkOutCntValue.text = String.format("%d회",cnt)
        binding.tvTotalWorkOutValue.text = String.format("%d분",totalWorkoutTime.toInt())

        binding.chartWorkOut.data = LineData(workOutSet(entryList))
        binding.chartWorkOut.invalidate()
    }

    private fun workOutSet(data : List<Entry>) : LineDataSet{
        val set = LineDataSet(data,resources.getString(R.string.work_out_title))
        set.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = resources.getColor(R.color.ok_btn,null)
            setCircleColor(resources.getColor(R.color.ok_btn,null))
            valueTextSize = 10f
            valueFormatter = WorkOutValueFormatter()
            lineWidth = 1.5f
            circleRadius = 3f
            fillAlpha = 0
            fillColor = resources.getColor(R.color.ok_btn,null)
            isHighlightEnabled = false
            setDrawValues(true)
            return this
        }
    }

    private fun initListener(){
        binding.btnDate.setOnClickListener {
            BottomMonthDialog(binding.btnDate.text.toString()).show(parentFragmentManager, "month")
        }
    }

    fun initGraph(data : MonthSelectData){
        binding.btnDate.text = String.format("%d.%s", data.year, Utils.dateText(data.month))
        viewModel.initWeight(data.year,data.month)
    }

    inner class WeightValueFormatter : ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return DecimalFormat("###,###,##0.0").format(value)
        }
    }

    inner class WorkOutValueFormatter : ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return String.format("%d",value.toInt())
        }
    }

}