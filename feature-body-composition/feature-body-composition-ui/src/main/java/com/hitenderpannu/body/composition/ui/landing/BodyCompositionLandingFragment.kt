package com.hitenderpannu.body.composition.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hitenderpannu.body.composition.databinding.FragmentBodyCompositionLandingBinding

class BodyCompositionLandingFragment : Fragment() {

    private lateinit var binding: FragmentBodyCompositionLandingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBodyCompositionLandingBinding.inflate(inflater)
        return binding.root
    }
}
