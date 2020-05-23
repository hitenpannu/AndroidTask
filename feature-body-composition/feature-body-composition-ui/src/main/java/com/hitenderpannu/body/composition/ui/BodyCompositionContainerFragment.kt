package com.hitenderpannu.body.composition.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hitenderpannu.body.composition.databinding.FragmentBodyCompositionContainerBinding

class BodyCompositionContainerFragment : Fragment() {

    private lateinit var binding: FragmentBodyCompositionContainerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBodyCompositionContainerBinding.inflate(inflater)
        return binding.root
    }
}
