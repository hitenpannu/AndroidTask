package com.hitenderpannu.dynamictaskfeature.di.modules

import com.hitenderpannu.common.domain.Mapper
import com.hitenderpannu.dynamictaskfeature.data.database.DataToDomainMapper
import com.hitenderpannu.dynamictaskfeature.data.database.DatabaseManager
import com.hitenderpannu.dynamictaskfeature.data.database.dao.TaskDao
import com.hitenderpannu.dynamictaskfeature.data.database.entity.TaskEntity
import com.hitenderpannu.dynamictaskfeature.data.database.repository.LocalTaskRepositoryImpl
import com.hitenderpannu.dynamictaskfeature.data.remote.TaskApi
import com.hitenderpannu.dynamictaskfeature.data.remote.TaskRepoImpl
import com.hitenderpannu.dynamictaskfeature.domain.TaskInteractor
import com.hitenderpannu.dynamictaskfeature.domain.TaskInteractorImpl
import com.hitenderpannu.dynamictaskfeature.domain.repo.LocalTaskRepo
import com.hitenderpannu.dynamictaskfeature.domain.repo.RemoteTaskRepo
import com.hitenderpannu.dynamictaskfeature.entity.Task
import com.hitenderpannu.dynamictaskfeature.ui.TaskFragment
import com.hitenderpannu.dynamictaskfeature.ui.TaskListAdapter
import com.hitenderpannu.dynamictaskfeature.util.ViewModelFactory
import com.hitenderpannu.dynamictaskfeature.di.TaskFragmentScope
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
