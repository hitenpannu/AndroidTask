package com.hitenderpannu.taskapp.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.hitenderpannu.taskapp.DynamicFeatureManager
import com.hitenderpannu.taskapp.R
import com.hitenderpannu.taskapp.databinding.ActivityHomeBinding
import com.hitenderpannu.taskapp.di.CustomViewModelFactory
import com.hitenderpannu.taskapp.di.DaggerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    @Inject
    lateinit var dynamicFeatureManager: DynamicFeatureManager

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

        binding?.bottomNavigationBar?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_dashboard -> findNavController(R.id.nav_host_fragment).navigate(R.id.workout)
                R.id.nav_tasks -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.taskManager)
//                    CoroutineScope(Dispatchers.IO).launch {
//                        if (dynamicFeatureManager.isInstalled(DynamicFeatureManager.FEATURE.TASK)) {
//                            findNavController(R.id.nav_host_fragment).navigate(R.id.taskManager)
//                        } else {
//                            val channel = dynamicFeatureManager.install(DynamicFeatureManager.FEATURE.TASK)
//                            while (!channel.isClosedForReceive && !channel.isClosedForSend) {
//                                val result = channel.receive()
//                                if (result.isSuccess) {
//                                    result.onSuccess { item ->
//                                        findNavController(R.id.nav_host_fragment).navigate(R.id.taskManager)
//                                        Log.e("RESULT", item.first)
//                                    }
//                                    result.onFailure {
//                                        Log.e("RESULT", it.message)
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
