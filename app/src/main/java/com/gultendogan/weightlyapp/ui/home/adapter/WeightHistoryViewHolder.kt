package com.gultendogan.weightlyapp.ui.home.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gultendogan.weightlyapp.databinding.ItemWeightHistoryBinding
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel

const val DATE_FORMAT = "dd MMM yyyy"

class WeightHistoryViewHolder(
    view: View,
    private val onClickWeight: ((weight: WeightUIModel) -> Unit)?
) :
    RecyclerView.ViewHolder(view) {
    private val binding = ItemWeightHistoryBinding.bind(view)

    fun bind(uiModel: WeightUIModel) = with(binding) {
        /*
        tvNote.text = uiModel.note
        tvEmoji.text = uiModel.emoji
        tvNote.isGone = uiModel.note.isBlank()
        tvDate.text = uiModel.date.toFormat(DATE_FORMAT)
        tvWeight.text = uiModel.valueWithUnit
        tvDifference.text = uiModel.difference
        tvDifference.setTextColor(ContextCompat.getColor(binding.root.context, uiModel.differenceColor))

         */
        itemView.setOnClickListener {
            onClickWeight?.invoke(uiModel)
        }
    }
}