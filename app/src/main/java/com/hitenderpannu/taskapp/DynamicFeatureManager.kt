package com.hitenderpannu.taskapp

import android.app.Activity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DynamicFeatureManager(val activity: Activity) {

    enum class FEATURE(val packageName: String, val displayName: String) {
        TASK(":feature-task:feature_task_ui", "Task")
    }

    private val manager: SplitInstallManager

    init {
        manager = SplitInstallManagerFactory.create(activity)
    }

    fun getListOfAvailableFeatures(): List<FEATURE> {
        val installedModules = manager.installedModules
        val installedFeatures = mutableListOf<FEATURE>()
        installedModules.forEach { module ->
            installedFeatures.add(
                FEATURE.values().first { it.packageName.contains(module) }
            )
        }
        return installedFeatures
    }

    suspend fun install(feature: FEATURE): Flow<Result<Pair<String, Int>>> {
        val request = SplitInstallRequest.newBuilder()
            .addModule(feature.packageName).build()

        return flow {
            manager.registerListener { state ->
                CoroutineScope(Dispatchers.IO).launch {
                    val message = when (state.status()) {
                        SplitInstallSessionStatus.CANCELED -> {
                            "Install Cancelled"
                        }
                        SplitInstallSessionStatus.CANCELING -> {
                            "Installation Cancelled"
                        }
                        SplitInstallSessionStatus.DOWNLOADED -> {
                            "Downloaded"
                        }
                        SplitInstallSessionStatus.DOWNLOADING -> {
                            "Downloading ${(state.bytesDownloaded() * 100).div(state.totalBytesToDownload())}%"
                        }
                        SplitInstallSessionStatus.FAILED -> {
                            "Failed"
                        }
                        SplitInstallSessionStatus.INSTALLED -> {
                            "Installed"
                        }
                        SplitInstallSessionStatus.INSTALLING -> {
                            "Installing"
                        }
                        SplitInstallSessionStatus.PENDING -> {
                            "Pending"
                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                            "Module too big required user confirmation"
                        }
                        SplitInstallSessionStatus.UNKNOWN -> {
                            state.errorCode().toString()
                        }
                        else -> ""
                    }
                    emit(Result.success(message to state.status()))
                }
            }
        }
    }
}
