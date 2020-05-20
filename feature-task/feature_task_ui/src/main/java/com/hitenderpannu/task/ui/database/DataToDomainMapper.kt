package com.hitenderpannu.task.ui.database

import com.hitenderpannu.common.domain.Mapper
import com.hitenderpannu.task.entity.Task
import com.hitenderpannu.task.ui.database.entity.TaskEntity

class DataToDomainMapper : Mapper<Task, TaskEntity> {
    override fun to(t: Task): TaskEntity {
        return TaskEntity(
            t.id,
            t.description,
            t.createdAt,
            t.updatedAt,
            t.isCompleted
        )
    }

    override fun from(r: TaskEntity): Task {
        return Task(
            r.id,
            r.description,
            r.createdAt,
            r.updatedAt,
            r.isCompleted
        )
    }
}
