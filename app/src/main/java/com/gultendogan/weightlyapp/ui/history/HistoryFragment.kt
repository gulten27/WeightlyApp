package com.gultendogan.weightlyapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentHistoryBinding
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.ui.home.adapter.WeightHistoryAdapter
import com.gultendogan.weightlyapp.ui.home.adapter.WeightItemDecorator
import com.gultendogan.weightlyapp.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BottomSheetDialogFragment() {

    private val viewModel: HistoryViewModel by viewModels()

    private val binding by viewBinding(FragmentHistoryBinding::bind)

    private val adapterWeightHistory: WeightHistoryAdapter by lazy {
        WeightHistoryAdapter(::onClickWeight)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun initViews() {
        initWeightRecyclerview()
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

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect(::setUIState)
        }
    }

    private fun setUIState(uiState: HistoryViewModel.UiState) = with(binding) {
        adapterWeightHistory.submitList(uiState.histories)
    }

    private fun onClickWeight(weight: WeightUIModel) {
        findNavController().navigate(HistoryFragmentDirections.actionNavigateAddWeight(weight))
    }

}