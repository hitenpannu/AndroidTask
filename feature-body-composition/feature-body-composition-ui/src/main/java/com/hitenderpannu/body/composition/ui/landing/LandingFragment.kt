package com.hitenderpannu.body.composition.ui.landing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.hitenderpannu.body.composition.databinding.FragmentBodyCompositionLandingBinding
import com.hitenderpannu.body.composition.di.DaggerManager
import com.hitenderpannu.body.composition.entity.PrimaryBodyComposition
import javax.inject.Inject

internal class LandingFragment : Fragment() {

    @Inject
    lateinit var adapter: LandingEntriesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentBodyCompositionLandingBinding

    private val viewModel: LandingFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LandingFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerManager.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBodyCompositionLandingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bodyCompositionListView.layoutManager = LinearLayoutManager(requireContext())
        binding.bodyCompositionListView.adapter = adapter

        viewModel.bodyCompositionEntries.observe(viewLifecycleOwner, bodyEntriesObserver)
    }

    private val bodyEntriesObserver = Observer<List<PrimaryBodyComposition>> {
        adapter.updateList(it)
    }
}
