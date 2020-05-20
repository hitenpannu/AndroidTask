package com.hitenderpannu.task.ui.di.modules

import com.hitenderpannu.common.domain.Mapper
import com.hitenderpannu.task.data.network.TaskApi
import com.hitenderpannu.task.data.network.TaskRepoImpl
import com.hitenderpannu.task.domain.TaskInteractor
import com.hitenderpannu.task.domain.TaskInteractorImpl
import com.hitenderpannu.task.domain.repo.LocalTaskRepo
import com.hitenderpannu.task.domain.repo.RemoteTaskRepo
import com.hitenderpannu.task.entity.Task
import com.hitenderpannu.task.ui.database.DataToDomainMapper
import com.hitenderpannu.task.ui.database.DatabaseManager
import com.hitenderpannu.task.ui.database.dao.TaskDao
import com.hitenderpannu.task.ui.database.entity.TaskEntity
import com.hitenderpannu.task.ui.database.repository.LocalTaskRepositoryImpl
import com.hitenderpannu.task.ui.di.TaskFragmentScope
import com.hitenderpannu.task.ui.taskFragment.TaskFragment
import com.hitenderpannu.task.ui.taskFragment.TaskListAdapter
import com.hitenderpannu.task.ui.util.ViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TaskFragmentModule(val fragment: TaskFragment) {

    @TaskFragmentScope
    @Provides
    fun provideTaskApi(retrofit: Retrofit) = retrofit.create(TaskApi::class.java)

    @TaskFragmentScope
    @Provides
    fun provideTaskRepo(taskApi: TaskApi): RemoteTaskRepo = TaskRepoImpl(taskApi)

    @TaskFragmentScope
    @Provides
    fun provideLocalTaskDao(): TaskDao {
        return DatabaseManager.getDatabase(fragment.requireContext().applicationContext).provideTaskDao()
    }

    @TaskFragmentScope
    @Provides
    fun provideDatabaseMapper(): Mapper<Task, TaskEntity> {
        return DataToDomainMapper()
    }

    @TaskFragmentScope
    @Provides
    fun provideLocalTaskRepo(taskDao: TaskDao, mapper: Mapper<Task, TaskEntity>): LocalTaskRepo {
        return LocalTaskRepositoryImpl(taskDao, mapper)
    }

    @TaskFragmentScope
    @Provides
    fun provideTaskInteractor(
        localTaskRepo: LocalTaskRepo
    ): TaskInteractor = TaskInteractorImpl(localTaskRepo)

    @TaskFragmentScope
    @Provides
    fun provideViewModelFactory(taskInteractor: TaskInteractor): ViewModelFactory =
        ViewModelFactory(taskInteractor)

    @TaskFragmentScope
    @Provides
    fun provideTaskListAdapter() = TaskListAdapter()
}
