package com.hitenderpannu.task.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat
import com.hitenderpannu.task.ui.databinding.ActivityTaskBinding
import com.hitenderpannu.task.ui.taskFragment.TaskFragment

class TaskActivity : AppCompatActivity() {

    private var binding: ActivityTaskBinding? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, TaskFragment())
            .commit()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
