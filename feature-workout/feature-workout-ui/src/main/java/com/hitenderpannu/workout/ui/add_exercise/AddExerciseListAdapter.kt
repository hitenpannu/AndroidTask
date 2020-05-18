package com.hitenderpannu.workout.ui.add_exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.ItemAddExerciseListBinding
import com.hitenderpannu.feature_dashboard_ui.databinding.ItemChipBinding
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.Exercise

class AddExerciseListAdapter(
    private var exerciseList: List<Exercise>
) : RecyclerView.Adapter<AddExerciseListAdapter.AddExerciseViewHolder>() {

    private var store: SelectedExerciseStore? = null

    fun updateExerciseList(exerciseList: List<Exercise>) {
        this.exerciseList = exerciseList;
        notifyDataSetChanged()
    }

    fun attachSelectedExerciseStore(selectedExerciseStore: SelectedExerciseStore) {
        store = selectedExerciseStore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddExerciseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_exercise_list, parent, false)
        val binding = ItemAddExerciseListBinding.bind(view)
        return AddExerciseViewHolder(binding)
    }

    override fun getItemCount() = exerciseList.size

    override fun onBindViewHolder(holder: AddExerciseViewHolder, position: Int) {
        with(exerciseList.get(position)) {
            val clickHandler = fun(isSelected: Boolean) {
                if (isSelected) {
                    store?.removeExercise(this)
                    notifyItemChanged(position)
                } else {
                    store?.addExercise(this)
                    notifyItemChanged(position)
                }
            }
            holder.bind(this, store?.isSelected(this) ?: false, clickHandler)

        }
    }

    inner class AddExerciseViewHolder(private val binding: ItemAddExerciseListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Exercise, isSelected: Boolean, clickHandler: (Boolean) -> Unit) {
            binding.exerciseName.text = item.name
            binding.bodyParts.removeAllViews()
            item.bodyParts.map { getBodyPartChip(it) }
                .forEach { binding.bodyParts.addView(it) }
            if (isSelected) {
                binding.actionAddRemove.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_remove))
            } else {
                binding.actionAddRemove.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_add))
            }
            binding.actionAddRemove.setOnClickListener { clickHandler(isSelected) }
        }

        private fun getBodyPartChip(bodyPart: BodyPart): View {
            val view = LayoutInflater.from(binding.root.context).inflate(R.layout.item_chip, null, false)
            val binding = ItemChipBinding.bind(view)
            binding.label.setText(bodyPart.name)
            return view
        }
    }
}
