package com.hitenderpannu.taskapp.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.hitenderpannu.base.BuildConfig
import com.hitenderpannu.taskapp.DynamicFeatureManager
import com.hitenderpannu.taskapp.databinding.ActivityHomeBinding
import com.hitenderpannu.taskapp.di.CustomViewModelFactory
import com.hitenderpannu.taskapp.di.DaggerManager
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]
    }
    private var binding: ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerManager.inject(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        viewModel.fetchUserData()
        attacheObservers()
    }

    private fun attacheObservers() {
        viewModel.userName().observe(this, userNameObserver)
        viewModel.userEmail().observe(this, userEmailObserver)
        viewModel.listOfFeatures().observe(this, installedFeatureListObserver)
    }

    private val userNameObserver = Observer<String> {
        binding?.userNameView?.text = it
    }

    private val userEmailObserver = Observer<String> {
        binding?.userEmailView?.text = it
    }

    private val installedFeatureListObserver = Observer<List<DynamicFeatureManager.FEATURE>> { features ->
        Log.e("TASK", features.joinToString(", "))
        val chips = features.map { feature ->
            Chip(this)
                .apply {
                    this.text = feature.displayName
                    this.setOnClickListener {
                        viewModel.launchFeature(feature)
                        val intent = Intent()
                        intent.setClassName("com.hitenderpannu.taskapp", "com.hitenderpannu.task.ui.TaskActivity")
                        startActivity(intent)
                    }
                }
        }
        chips.forEach { binding?.installedFeatures?.addView(it) }
    }

    override fun onDestroy() {
        binding = null
        removeObservers()
        super.onDestroy()
    }

    private fun removeObservers() {
        viewModel.userName().removeObserver(userNameObserver)
        viewModel.userEmail().removeObserver(userEmailObserver)
        viewModel.listOfFeatures().removeObserver(installedFeatureListObserver)
    }
}
