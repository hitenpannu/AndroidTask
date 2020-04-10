package com.hitenderpannu.taskapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.hitenderpannu.base.BuildConfig
import com.hitenderpannu.taskapp.databinding.ActivityMainBinding
import com.hitenderpannu.userlist.ui.UserListActivity

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val manager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding?.btnLaunchUserList?.setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
        }
        binding?.btnDownloadTaskFeature?.setOnClickListener {
            startDownloadingTaskFeature()
        }
        binding?.btnLaunchTask?.setOnClickListener {
            val intent = Intent()
            intent.setClassName(BuildConfig.APPLICATION_ID, "com.hitenderpannu.task.ui.TaskActivity")
            startActivity(intent)
        }
    }

    private fun startDownloadingTaskFeature() {
        val moduleToInstall = ":feature-task:feature-task-ui"

        if (manager.installedModules.contains(moduleToInstall)) {
            binding?.btnLaunchTask?.isEnabled = true
            return
        }

        val request = SplitInstallRequest.newBuilder()
            .addModule(moduleToInstall)
            .build()
        manager.startInstall(request)
        manager.registerListener { sessionState ->
            when (sessionState.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show()
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    Toast.makeText(this, "Installed", Toast.LENGTH_SHORT).show()
                    binding?.btnLaunchTask?.isEnabled = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
