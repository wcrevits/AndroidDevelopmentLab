package be.woutcrevits.androidapp.models

import java.util.Date

object MockDataSource {

    fun getUsers(): List<User> {
        return listOf(
            User(1, "woutc", "Wout", "Crevits", "password123", true),
            User(2, "janp", "Jan", "Peeters", "secret456", true),
            User(3, "elined", "Eline", "Devos", "qwerty789", false)
        )
    }

    fun getToDos(): List<ToDo> {
        val users = getUsers()
        val user1 = users[0]
        val user2 = users[1]
        val user3 = users[2]

        val now = Date()

        return listOf(
            // 1. NEW status (assignedToUser = null, finishedOnDate = null)
            ToDo(
                number = 1,
                title = "Setup Database",
                description = "Configure Room database and entities.",
                createdByUser = user1,
                createOnDate = now,
                assignedToUser = null,
                finishedOnDate = null,
                timeEstimated = 120,
                analysisDone = false,
                developmentDone = false,
                reviewAndTestingDone = false,
                acceptanceDone = false
            ),
            // 2. ASSIGNED status (assignedToUser != null, finishedOnDate = null)
            ToDo(
                number = 2,
                title = "Implement Login Screen",
                description = "Create Jetpack Compose UI for login and authentication.",
                createdByUser = user1,
                createOnDate = now,
                assignedToUser = user2,
                finishedOnDate = null,
                timeEstimated = 200,
                analysisDone = true,
                developmentDone = false,
                reviewAndTestingDone = false,
                acceptanceDone = false
            ),
            // 3. ASSIGNED with analysis & development done
            ToDo(
                number = 3,
                title = "API Integration",
                description = "Connect app to backend REST services using Retrofit.",
                createdByUser = user2,
                createOnDate = now,
                assignedToUser = user3,
                finishedOnDate = null,
                timeEstimated = 160,
                analysisDone = true,
                developmentDone = true,
                reviewAndTestingDone = false,
                acceptanceDone = false
            ),
            // 4. FINISHED status (assignedToUser != null, finishedOnDate != null)
            ToDo(
                number = 4,
                title = "Project Setup",
                description = "Initial project skeleton and Gradle setup.",
                createdByUser = user1,
                createOnDate = now,
                assignedToUser = user1,
                finishedOnDate = now,
                timeEstimated = 80,
                analysisDone = true,
                developmentDone = true,
                reviewAndTestingDone = true,
                acceptanceDone = true
            ),
            // 5. NEW status
            ToDo(
                number = 5,
                title = "Write Unit Tests",
                description = "Add unit tests for viewmodels and models.",
                createdByUser = user3,
                createOnDate = now,
                assignedToUser = null,
                finishedOnDate = null,
                timeEstimated = 100,
                analysisDone = false,
                developmentDone = false,
                reviewAndTestingDone = false,
                acceptanceDone = false
            )
        )
    }
}
