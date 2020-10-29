package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.*
import pl.polsl.workflow.manager.client.model.remote.data.*
import java.time.Instant

fun TaskApiModel.map(tasks: Map<Long, TaskApiModel>?, localizations: Map<Long, LocalizationApiModel>, users: Map<Long, UserApiModel>): Task {
    return Task(
            id = id,
            assignDate = assignDate,
            description = description,
            createDate = createDate,
            deadline = deadline,
            estimatedExecutionTime = Instant.ofEpochMilli(estimatedExecutionTimeInMillis),
            localization = localizations.getValue(localizationId).map(),
            name = name,
            superTask = tasks?.values?.find {
                it.sharedTaskId == sharedTaskId && it.taskManagerReportModel?.fixTaskId == id
            }?.map(tasks, localizations, users),
            startDate = startDate,
            taskManagerReport = taskManagerReportModel?.map(tasks, localizations, users),
            taskWorkerReport = taskWorkerReportApiModel?.map(),
            assignedWorker = workerId?.let { users.getValue(it) }?.map(),
    )
}

fun TaskWorkerReportApiModel.map(): TaskWorkerReport {
    return TaskWorkerReport(
            id = id,
            description = description,
            date = date,
            success = success
    )
}

fun TaskManagerReportApiModel.map(tasks: Map<Long, TaskApiModel>?, localizations: Map<Long, LocalizationApiModel>, users: Map<Long, UserApiModel>): TaskManagerReport {
    return TaskManagerReport(
            id = id,
            date = date,
            description = description,
            fixTask = fixTaskId?.let { tasks?.getValue(it) }?.map(tasks, localizations, users)
    )
}

fun TaskManagerReportApiModel.map(): TaskManagerReport {
    return TaskManagerReport(
            id = id,
            date = date,
            description = description,
            fixTask = null
    )
}

fun TaskManagerReportPost.map(): TaskManagerReportApiModelPost {
    return TaskManagerReportApiModelPost(
            description = description,
            taskId = task.id
    )
}

fun TaskPost.map(): TaskApiModelPost {
    return TaskApiModelPost(
            groupId = group.id,
            description = description,
            deadline = deadline,
            name = name,
            workerId = worker?.id,
            estimatedExecutionTimeInMillis = estimatedExecutionTime.toEpochMilli(),
            localizationId = localization.id,
            subTaskId = subTask?.id
    )
}

fun TaskWorkerReportPost.map(): TaskWorkerReportApiModelPost {
    return TaskWorkerReportApiModelPost(
        description = description,
        success = success,
        taskId = task.id
    )
}

