package com.gultendogan.weightlyapp.ui.home.chart

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.gultendogan.weightlyapp.utils.extensions.orZero

class WeightMarkerView(context: Context, var histories: List<WeightUIModel?>) :
    MarkerView(context, R.layout.marker_view_weight) {

    private val tvMarkerTitle = findViewById<TextView>(R.id.tvMarkerTitle);

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val history = histories[e?.x.orZero().toInt()]
        val stringBuilder = StringBuilder()
        stringBuilder.append(history?.formattedValue)
        stringBuilder.append("\n")
        stringBuilder.append(history?.formattedDate)
        tvMarkerTitle.text = stringBuilder.toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

}