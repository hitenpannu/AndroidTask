package com.hitenderpannu.taskapp.launcher

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.hitenderpannu.auth.ui.AuthRouter
import com.hitenderpannu.base.BuildConfig
import com.hitenderpannu.taskapp.databinding.ActivityMainBinding
import com.hitenderpannu.taskapp.di.DaggerManager
import javax.inject.Inject

class LauncherActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LauncherViewModel

    private var binding: ActivityMainBinding? = null

    private val manager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerManager.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding?.btnDownloadTaskFeature?.setOnClickListener {
            startDownloadingTaskFeature()
        }
        binding?.btnLaunchTask?.setOnClickListener {
            val intent = Intent()
            intent.setClassName("com.hitenderpannu.taskapp", "com.hitenderpannu.task.ui.TaskActivity")
            startActivity(intent)
        }
    }

    private fun startDownloadingTaskFeature() {
        val moduleToInstall = ":feature-task:feature_task_ui"

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            AuthRouter.REQUEST_AUTHENTICATION -> viewModel.checkIfUserIsLoggedIn()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
