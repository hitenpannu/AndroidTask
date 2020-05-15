package com.hitenderpannu.taskapp.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
