package com.hitenderpannu.task.ui.di.modules

import com.hitenderpannu.common.utils.NetworkConnectionChecker
import com.hitenderpannu.task.data.network.TaskApi
import com.hitenderpannu.task.data.network.TaskRepo
import com.hitenderpannu.task.data.network.TaskRepoImpl
import com.hitenderpannu.task.domain.TaskInteractor
import com.hitenderpannu.task.domain.TaskInteractorImpl
import com.hitenderpannu.task.ui.util.ViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CommonModule {

    @Provides
    fun provideTaskApi(retrofit: Retrofit) = retrofit.create(TaskApi::class.java)

    @Provides
    fun provideTaskRepo(taskApi: TaskApi): TaskRepo = TaskRepoImpl(taskApi)

    @Provides
    fun provideTaskInteractor(
        networkConnectionChecker: NetworkConnectionChecker,
        taskRepo: TaskRepo
    ): TaskInteractor = TaskInteractorImpl(networkConnectionChecker, taskRepo)

    @Provides
    fun provideViewModelFactory(taskInteractor: TaskInteractor): ViewModelFactory =
        ViewModelFactory(taskInteractor)
}
