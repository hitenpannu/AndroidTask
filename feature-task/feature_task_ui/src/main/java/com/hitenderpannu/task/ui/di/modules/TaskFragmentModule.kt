package com.hitenderpannu.task.ui.di.modules

import com.hitenderpannu.task.ui.di.TaskFragmentScope
import com.hitenderpannu.task.ui.taskFragment.TaskFragment
import com.hitenderpannu.task.ui.taskFragment.TaskListAdapter
import dagger.Module
import dagger.Provides

@Module
class TaskFragmentModule(val fragment: TaskFragment) {

    @TaskFragmentScope
    @Provides
    fun provideTaskListAdapter() = TaskListAdapter()
}
