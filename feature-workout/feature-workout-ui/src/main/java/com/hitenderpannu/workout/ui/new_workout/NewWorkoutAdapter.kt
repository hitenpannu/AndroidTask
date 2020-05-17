package com.hitenderpannu.workout.ui.new_workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.ItemExerciseSetBinding
import com.hitenderpannu.feature_dashboard_ui.databinding.ItemWorkoutExerciseBinding
import com.hitenderpannu.workout.entity.Exercise
import com.hitenderpannu.workout.entity.ExerciseSet
import com.hitenderpannu.workout.entity.WorkoutExercise
import com.hitenderpannu.workout.entity.WorkoutWithExercises

class NewWorkoutAdapter(
    var workoutId: Long? = null,
    var exerciseList: MutableList<WorkoutExercise> = mutableListOf(),
    var exerciseCallback: ExerciseCallback? = null
) :
    RecyclerView.Adapter<NewWorkoutAdapter.NewWorkoutViewHolder>() {

    fun attachExerciseCallback(callback: ExerciseCallback) {
        this.exerciseCallback = callback
    }

    fun updateWorkout(newWorkout: WorkoutWithExercises) {
        this.workoutId = newWorkout.workoutId
        this.exerciseList = mutableListOf()
        this.exerciseList.addAll(newWorkout.exercises)
        notifyDataSetChanged()
    }

    fun updateExercise(workoutExercise: WorkoutExercise) {
        val position = this.exerciseList.indexOfFirst { it.exercise.id == workoutExercise.exercise.id }
        this.exerciseList.removeAt(position)
        this.exerciseList.add(position, workoutExercise)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewWorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_exercise, parent, false)
        val binding = ItemWorkoutExerciseBinding.bind(view)
        return NewWorkoutViewHolder(binding)
    }

    override fun getItemCount() = this.exerciseList.size

    override fun onBindViewHolder(holder: NewWorkoutViewHolder, position: Int) {
        holder.bind(this.exerciseList[position])
    }

    inner class NewWorkoutViewHolder(private val binding: ItemWorkoutExerciseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: WorkoutExercise) {
            updateExerciseInformation(exercise.exercise)
            updateSetsInformation(exercise.sets)
            binding.addSetButton.setOnClickListener {
                exerciseCallback?.addSetButtonClicked(exercise.workoutId, exercise.exercise.id)
            }
        }

        private fun updateSetsInformation(sets: List<ExerciseSet>) {
            binding.setsContainer.removeAllViews()
            sets.forEachIndexed { index, exerciseSet ->
                val setView = LayoutInflater.from(binding.root.context).inflate(R.layout.item_exercise_set, null, false)
                val setBinding = ItemExerciseSetBinding.bind(setView)
                setBinding.setIndexLabel.text = "Set " + (index + 1)
                if (exerciseSet.repCount != 0) {
                    setBinding.repCountInput.setText(exerciseSet.repCount.toString())
                }
                if (exerciseSet.weight.toInt() != 0) {
                    setBinding.weightInput.setText(exerciseSet.weight.toDouble().toString())
                }

                setBinding.repCountInput.doAfterTextChanged {
                    val repCount = it?.toString()?.takeIf { it.isNotBlank() }?.toInt() ?: 0
                    exerciseCallback?.updateReps(exerciseSet.setId, repCount)
                }

                setBinding.weightInput.doAfterTextChanged {
                    val weight = it?.toString()?.takeIf { it.isNotBlank() }?.toDouble() ?: 0.0
                    exerciseCallback?.updateWeight(exerciseSet.setId, weight)
                }

                binding.setsContainer.addView(setBinding.root)
            }
        }

        private fun updateExerciseInformation(exercise: Exercise) {
            binding.exerciseName.text = exercise.name
            binding.bodyParts.text = exercise.bodyParts.map { it.name }.joinToString(", ")
            binding.equipments.text = exercise.equipments.map { it.name }.joinToString(", ")
        }
    }

    interface ExerciseCallback {
        fun addSetButtonClicked(workoutId: Long, exerciseId: String)
        fun updateReps(setId: Long, newRepCount: Int)
        fun updateWeight(setId: Long, weight: Double)
    }
}
