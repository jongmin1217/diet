package com.bellminp.diet.ui.main.fragment

import androidx.fragment.app.activityViewModels
import com.bellminp.diet.R
import com.bellminp.diet.databinding.FragmentCalendarBinding
import com.bellminp.diet.databinding.FragmentGraphBinding
import com.bellminp.diet.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GraphFragment : BaseFragment<FragmentGraphBinding, GraphViewModel>(R.layout.fragment_graph) {
    override val viewModel by activityViewModels<GraphViewModel>()

    override fun initBinding() {
        binding.vm = viewModel
    }
}