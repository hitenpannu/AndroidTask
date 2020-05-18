package com.hitenderpannu.workout.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.hitenderpannu.feature_dashboard_ui.R
import com.hitenderpannu.feature_dashboard_ui.databinding.ItemChipBinding
import com.hitenderpannu.feature_dashboard_ui.databinding.ViewWorkoutAnalysisBinding
import com.hitenderpannu.workout.entity.BodyPart
import com.hitenderpannu.workout.entity.WorkoutWithExercises

class WorkoutShortSummary : FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    private var binding: ViewWorkoutAnalysisBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_workout_analysis, this)
        binding = ViewWorkoutAnalysisBinding.bind(view)
    }

    fun showAnalysisFor(workout: WorkoutWithExercises) {
        inflateBodyParts(workout)
        binding.weightLiftedInLastWorkout.text = formattedWeight(workout)
        binding.setsInLastWorkout.text = totalNumberOfSets(workout).toString()
    }

    private fun formattedWeight(workout: WorkoutWithExercises): String {
        return workout.exercises.map { it.sets.sumByDouble { it.weight.toDouble() } }.sum().toString() + " Kg"
    }

    private fun totalNumberOfSets(workout: WorkoutWithExercises): Int {
        return workout.exercises.map { it.sets.map { it.repCount } }.flatten().sum()
    }

    private fun inflateBodyParts(workout: WorkoutWithExercises) {
        val listOfAllCoveredBodyParts = getListOfFocusedBodyParts(workout)
        val bodyPartsViews = listOfAllCoveredBodyParts.map { mapToChipView(it) }
        binding.focusedBodyPartsContainer.removeAllViews()
        for (bodyPartsView in bodyPartsViews) {
            binding.focusedBodyPartsContainer.addView(bodyPartsView)
        }
    }

    private fun getListOfFocusedBodyParts(workout: WorkoutWithExercises): List<BodyPart> {
        val setOfAllBodyParts = mutableSetOf<BodyPart>()
        val listOfAllExercises = workout.exercises.map { it.exercise }
        listOfAllExercises.map { it.bodyParts }.forEach { bodyParts -> setOfAllBodyParts.addAll(bodyParts) }
        return setOfAllBodyParts.filter { bodyPart ->
            listOfAllExercises.all { it.bodyParts.any { it.id == bodyPart.id } }
        }
    }

    private fun mapToChipView(bodyPart: BodyPart): View {
        val view = LayoutInflater.from(binding.root.context).inflate(R.layout.item_chip, null, false)
        val binding = ItemChipBinding.bind(view)
        binding.label.setText(bodyPart.name)
        return binding.root
    }
}
