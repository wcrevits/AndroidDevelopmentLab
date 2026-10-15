package be.woutcrevits.androidapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.woutcrevits.androidapp.R
import be.woutcrevits.androidapp.models.MockDataSource
import be.woutcrevits.androidapp.models.ToDo
import be.woutcrevits.androidapp.models.User
import be.woutcrevits.androidapp.ui.theme.AndroidAppTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditToDoScreen(
    toDo: ToDo? = null,
    onSave: (ToDo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(toDo?.title ?: "") }
    var description by remember { mutableStateOf(toDo?.description ?: "") }
    var assignedToUser by remember { mutableStateOf<User?>(toDo?.assignedToUser) }
    var analysisDone by remember { mutableStateOf(toDo?.analysisDone ?: false) }
    var developmentDone by remember { mutableStateOf(toDo?.developmentDone ?: false) }
    var reviewAndTestingDone by remember { mutableStateOf(toDo?.reviewAndTestingDone ?: false) }
    var acceptanceDone by remember { mutableStateOf(toDo?.acceptanceDone ?: false) }

    var expanded by remember { mutableStateOf(false) }
    val users = remember { MockDataSource.getUsers() }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top row: Logo + Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_todo_logo),
                    contentDescription = "TODO Logo",
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = if (toDo == null) "Add ToDo" else "Edit ToDo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Red line
            HorizontalDivider(
                color = Color.Red,
                thickness = 2.dp
            )

            // Form Fields
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Assigned To Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = assignedToUser?.let { "${it.firstName} ${it.lastName} (${it.userName})" } ?: "Select User (Optional)",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Assigned To") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Select User (Optional)") },
                        onClick = {
                            assignedToUser = null
                            expanded = false
                        }
                    )
                    users.forEach { user ->
                        DropdownMenuItem(
                            text = { Text("${user.firstName} ${user.lastName} (${user.userName})") },
                            onClick = {
                                assignedToUser = user
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Switches for progress steps
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Progress Steps",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Analysis Done")
                        Switch(checked = analysisDone, onCheckedChange = { analysisDone = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Development Done")
                        Switch(checked = developmentDone, onCheckedChange = { developmentDone = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Review & Testing Done")
                        Switch(checked = reviewAndTestingDone, onCheckedChange = { reviewAndTestingDone = it })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Acceptance Done")
                        Switch(checked = acceptanceDone, onCheckedChange = { acceptanceDone = it })
                    }
                }
            }

            // Save Button
            Button(
                onClick = {
                    val updatedToDo = ToDo(
                        number = toDo?.number ?: 1,
                        title = title,
                        description = description,
                        createdByUser = toDo?.createdByUser ?: User(1, "default", "User", "", "", true),
                        createOnDate = toDo?.createOnDate ?: Date(),
                        assignedToUser = assignedToUser,
                        finishedOnDate = if (acceptanceDone) (toDo?.finishedOnDate ?: Date()) else null,
                        timeEstimated = toDo?.timeEstimated ?: 120,
                        analysisDone = analysisDone,
                        developmentDone = developmentDone,
                        reviewAndTestingDone = reviewAndTestingDone,
                        acceptanceDone = acceptanceDone
                    )
                    onSave(updatedToDo)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save ToDo")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddEditToDoScreenPreview() {
    AndroidAppTheme {
        AddEditToDoScreen()
    }
}
