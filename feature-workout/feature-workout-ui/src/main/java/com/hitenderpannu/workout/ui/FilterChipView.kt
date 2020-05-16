package com.hitenderpannu.workout.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.ItemFilterChipBinding
import kotlin.properties.Delegates

class FilterChipView : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private val binding: ItemFilterChipBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_filter_chip, this)
        binding = ItemFilterChipBinding.bind(view)

        binding.root.setOnClickListener {
            isFilterSelected = !isFilterSelected
        }
    }

    private var uniqueId: String = ""

    var isFilterSelected: Boolean by Delegates.observable(false, onChange = { property, oldValue, newValue ->
        if (newValue) updateStyleForSelected() else updateStyleForUnSelected()
    })

    fun setText(name: String, id: String) {
        binding.label.text = name
        uniqueId = id
    }

    private fun updateStyleForUnSelected() {
        binding.label.background = ContextCompat.getDrawable(context, R.drawable.filter_chip_background_un_selected)
        binding.label.setTextColor(ContextCompat.getColor(context, R.color.white))
    }

    private fun updateStyleForSelected() {
        binding.label.background = ContextCompat.getDrawable(context, R.drawable.filter_chip_background_selected)
        binding.label.setTextColor(ContextCompat.getColor(context, R.color.blush))
    }
}
