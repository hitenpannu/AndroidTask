package com.hitenderpannu.workout.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hitenderpannu.base.BaseFragment
import com.hitenderpannu.feature_dashboard_ui.R

class DashBoardFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dash_boards, container, false)
        view.setTag(
            R.id.fragment_container_view_tag, DashBoardFragment::class.java.name
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatButton>(R.id.createFirstWorkoutButton).setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_addExerciseFragment)
        }
    }
}
