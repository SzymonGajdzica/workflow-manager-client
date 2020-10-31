package pl.polsl.workflow.manager.client.model.remote.mapper

import pl.polsl.workflow.manager.client.model.data.*
import pl.polsl.workflow.manager.client.model.remote.data.*
import java.time.Instant

fun TaskApiModel.map(tasks: Map<Long, TaskApiModel>?, localizations: Map<Long, LocalizationApiModel>, users: Map<Long, UserApiModel>, groups: Map<Long, GroupApiModel>): Task {
    return Task(
            id = id,
            assignDate = assignDate,
            description = description,
            createDate = createDate,
            deadline = deadline,
            estimatedExecutionTime = Instant.ofEpochMilli(estimatedExecutionTimeInMillis),
            localization = localizations.getValue(localizationId).map(),
            name = name,
            startDate = startDate,
            sharedTaskId = sharedTaskId,
            isSubTask = isSubTask,
            taskManagerReport = taskManagerReportModel?.map(tasks, localizations, users, groups),
            taskWorkerReport = taskWorkerReportApiModel?.map(),
            assignedWorker = workerId?.let { users.getValue(it) }?.map(),
            group = groups.getValue(groupId).map(users)
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

fun TaskManagerReportApiModel.map(tasks: Map<Long, TaskApiModel>?, localizations: Map<Long, LocalizationApiModel>, users: Map<Long, UserApiModel>, groups: Map<Long, GroupApiModel>): TaskManagerReport {
    return TaskManagerReport(
            id = id,
            date = date,
            description = description,
            fixTask = when {
                fixTaskId == null -> null
                tasks?.containsKey(fixTaskId) == true -> AllowableValue.Allowed(tasks.getValue(fixTaskId).map(tasks, localizations, users, groups))
                tasks?.containsKey(fixTaskId) == false -> AllowableValue.NotAllowed(fixTaskId)
                else -> null
            }
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

