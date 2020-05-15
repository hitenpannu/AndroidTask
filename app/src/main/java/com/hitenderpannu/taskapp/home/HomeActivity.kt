package com.hitenderpannu.taskapp.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
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

        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
