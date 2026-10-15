package be.woutcrevits.androidapp.models

import java.util.Date

enum class Status {
    NEW, ASSIGNED, FINISHED
}

data class ToDo(
    var number: Int,
    var title: String,
    var description: String,
    var createdByUser: User,
    var createOnDate: Date,
    var assignedToUser: User?,
    var finishedOnDate: Date?,
    var timeEstimated: Int,
    var analysisDone: Boolean,
    var developmentDone: Boolean,
    var reviewAndTestingDone: Boolean,
    var acceptanceDone: Boolean
) {
    constructor() : this(
        number = 0,
        title = "",
        description = "",
        createdByUser = User(),
        createOnDate = Date(),
        assignedToUser = null,
        finishedOnDate = null,
        timeEstimated = 0,
        analysisDone = false,
        developmentDone = false,
        reviewAndTestingDone = false,
        acceptanceDone = false
    )

    val status: Status
        get() {
            return if (assignedToUser != null && finishedOnDate != null) {
                Status.FINISHED
            } else if (assignedToUser != null) {
                Status.ASSIGNED
            } else {
                Status.NEW
            }
        }

    val statusDescription: String
        get() = when (status) {
            Status.NEW -> "New"
            Status.ASSIGNED -> "Assigned"
            Status.FINISHED -> "Finished"
        }

    val timeRemaining: Int
        get() {
            return if (acceptanceDone) {
                0
            } else if (reviewAndTestingDone) {
                (timeEstimated * 0.10).toInt()
            } else if (developmentDone) {
                (timeEstimated * 0.30).toInt()
            } else if (analysisDone) {
                (timeEstimated * 0.85).toInt()
            } else {
                timeEstimated
            }
        }
}
