package com.gultendogan.weightlyapp.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.databinding.FragmentOnBoardingBinding
import com.gultendogan.weightlyapp.uicomponents.CardRuler
import com.gultendogan.weightlyapp.utils.viewBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment(R.layout.fragment_on_boarding) {

    private val binding by viewBinding(FragmentOnBoardingBinding::bind)

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observe()
    }

    private fun observe() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    OnBoardingViewModel.Event.NavigateToHome -> {
                        findNavController().navigate(OnBoardingFragmentDirections.actionNavigateHome())
                    }
                }
            }
        }
    }


    private fun initViews() = with(binding) {
        cardRulerCurrent.render(CardRuler(R.string.current_weight, R.string.enter_current_weight))
        cardRulerGoal.render(CardRuler(R.string.goal_weight, R.string.enter_goal_weight))
        btnContinue.setOnClickListener {
            val currentWeight: Float = cardRulerCurrent.value
            val goalWeight: Float = cardRulerGoal.value
            viewModel.save(currentWeight = currentWeight, goalWeight = goalWeight)
        }
    }

}