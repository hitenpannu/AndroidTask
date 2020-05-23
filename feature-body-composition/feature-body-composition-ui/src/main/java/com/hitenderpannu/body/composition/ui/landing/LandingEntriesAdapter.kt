package com.hitenderpannu.body.composition.ui.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.body.composition.R
import com.hitenderpannu.body.composition.databinding.ItemLandingCompositionEntryBinding
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition

internal class LandingEntriesAdapter(
    private val listOfEntries: MutableList<PrimaryBodyComposition> = mutableListOf()
) :
    RecyclerView.Adapter<LandingEntriesAdapter.ViewHolder>() {

    companion object {
        private const val HEADER_TYPE = 0
        private const val ENTRY_TYPE = 1
    }

    fun updateList(list: List<PrimaryBodyComposition>) {
        listOfEntries.clear()
        listOfEntries.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            HEADER_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_landing_composition_entry, parent, false)
                val binding = ItemLandingCompositionEntryBinding.bind(view)
                ViewHolder.HeaderViewHolder(binding)
            }
            ENTRY_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_landing_composition_entry, parent, false)
                val binding = ItemLandingCompositionEntryBinding.bind(view)
                ViewHolder.EntriesViewHolder(binding)
            }
            else -> {
                throw IllegalArgumentException("Unsupported View Type")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (listOfEntries.isEmpty()) 0
        else listOfEntries.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) return HEADER_TYPE else ENTRY_TYPE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.EntriesViewHolder -> holder.bind(listOfEntries[position - 1])
            is ViewHolder.HeaderViewHolder -> holder.bind()
        }
    }

    sealed class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        class EntriesViewHolder(val binding: ItemLandingCompositionEntryBinding) : ViewHolder(binding.root) {
            fun bind(primaryBodyComposition: PrimaryBodyComposition) {
                binding.totalWeight.run {
                    text = primaryBodyComposition.totalWeight.toPlainString()
                }
                binding.fatPercentage.run {
                    text = primaryBodyComposition.fatPercentage.toPlainString()
                }
                binding.muscleWeight.run {
                    text = primaryBodyComposition.muscleWeight.toPlainString()
                }
            }
        }

        class HeaderViewHolder(val binding: ItemLandingCompositionEntryBinding) : ViewHolder(binding.root) {
            fun bind() {
                binding.totalWeight.run {
                    text = context.getString(R.string.label_total_weight)
                }
                binding.fatPercentage.run {
                    text = context.getString(R.string.label_fat_percentage)
                }
                binding.muscleWeight.run {
                    text = context.getString(R.string.label_muscle_weight)
                }
            }
        }
    }
}
