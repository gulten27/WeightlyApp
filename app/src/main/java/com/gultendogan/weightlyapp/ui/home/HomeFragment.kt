package com.gultendogan.weightlyapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gultendogan.weightlyapp.ui.home.chart.ChartFeeder
import com.gultendogan.weightlyapp.ui.home.chart.ChartInitializer
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentHomeBinding
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.ui.home.adapter.WeightHistoryAdapter
import com.gultendogan.weightlyapp.ui.home.adapter.WeightItemDecorator
import com.gultendogan.weightlyapp.utils.viewBinding
import com.gultendogan.weightlyapp.uicomponents.InfoCardUIModel
import com.yonder.statelayout.State
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val adapterWeightHistory: WeightHistoryAdapter by lazy {
        WeightHistoryAdapter(::onClickWeight)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }
    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::setUIState)
        }
    }
    private fun setUIState(uiState: HomeViewModel.UiState) = with(binding) {
        if (uiState.shouldShowEmptyView) {
            stateLayout.setState(State.EMPTY)
        } else {
            stateLayout.setState(State.CONTENT)
            llInsightView.isVisible = uiState.shouldShowInsightView
            btnSeeAllHistory.isVisible = uiState.shouldShowAllWeightButton
            adapterWeightHistory.submitList(uiState.reversedHistories)
            ChartFeeder.setChartData(
                chart = lineChart,
                histories = uiState.histories,
                barEntries = uiState.barEntries,
                context = requireContext()
            )
            infoCardAverage.render(
                InfoCardUIModel(
                    title = uiState.averageWeight,
                    description = R.string.title_average_weight,
                    titleTextColor = R.color.orange
                )
            )
            infoCardMax.render(
                InfoCardUIModel(
                    title = uiState.maxWeight,
                    description = R.string.title_max_weight,
                    titleTextColor = R.color.red
                )
            )
            infoCardMin.render(
                InfoCardUIModel(
                    title = uiState.minWeight,
                    description = R.string.title_min_weight,
                    titleTextColor = R.color.green
                )
            )
            icCurrent.render(
                InfoCardUIModel(
                    title = uiState.currentWeight,
                    description = R.string.current,
                    titleTextColor = R.color.purple_500
                )
            )
            icGoal.render(
                InfoCardUIModel(
                    title = uiState.goalWeight,
                    description = R.string.goal
                )
            )
            icStart.render(
                InfoCardUIModel(
                    title = uiState.startWeight,
                    description = R.string.start
                )
            )
        }
    }
    private fun initViews() = with(binding) {
        initWeightRecyclerview()
        ChartInitializer.initBarChart(lineChart)
        btnSeeAllHistory.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigateHistory())
        }
    }
    private fun initWeightRecyclerview() = with(binding.rvWeightHistory) {
        adapter = adapterWeightHistory
        addItemDecoration(WeightItemDecorator(requireContext()))
        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
    private fun onClickWeight(weight: WeightUIModel) {
        findNavController().navigate(HomeFragmentDirections.actionNavigateAddWeight(weight))
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                findNavController().navigate(HomeFragmentDirections.actionNavigateAddWeight(null))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}