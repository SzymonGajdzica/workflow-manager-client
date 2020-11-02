package pl.polsl.workflow.manager.client.model.mapper

import pl.polsl.workflow.manager.client.model.data.*
import pl.polsl.workflow.manager.client.model.remote.data.*
import pl.polsl.workflow.manager.client.util.lazy.list.LazyList
import java.time.Instant

suspend fun TaskApiModel.map(
        safeTaskMap: Boolean,
        tasks: LazyList<TaskApiModel>?,
        localizations: LazyList<LocalizationApiModel>,
        users: LazyList<UserApiModel>,
        groups: LazyList<GroupApiModel>
): Task {
    return Task(
            id = id,
            assignDate = assignDate,
            description = description,
            createDate = createDate,
            deadline = deadline,
            estimatedExecutionTime = Instant.ofEpochMilli(estimatedExecutionTimeInMillis),
            localization = localizations.getItem(localizationId).map(),
            name = name,
            startDate = startDate,
            sharedTaskId = sharedTaskId,
            isSubTask = isSubTask,
            taskManagerReport = taskManagerReportModel?.map(safeTaskMap, tasks, localizations, users, groups),
            taskWorkerReport = taskWorkerReportApiModel?.map(),
            assignedWorker = workerId?.let { users.getItem(it) }?.map(),
            group = groups.getItem(groupId).map(users)
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

suspend fun TaskManagerReportApiModel.map(
        safeTaskMap: Boolean,
        tasks: LazyList<TaskApiModel>?,
        localizations: LazyList<LocalizationApiModel>,
        users: LazyList<UserApiModel>,
        groups: LazyList<GroupApiModel>
): TaskManagerReport {
    return TaskManagerReport(
            id = id,
            date = date,
            description = description,
            fixTask = fixTaskId?.let {
                val task = if(safeTaskMap) tasks?.safeGetItem(it) else tasks?.getItem(it)
                if(task != null)
                    AllowableValue.Allowed(task.map(safeTaskMap, tasks, localizations, users, groups))
                else
                    AllowableValue.NotAllowed(it)
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

