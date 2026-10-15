package be.woutcrevits.androidapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.woutcrevits.androidapp.R
import be.woutcrevits.androidapp.models.MockDataSource
import be.woutcrevits.androidapp.models.Status
import be.woutcrevits.androidapp.models.ToDo
import be.woutcrevits.androidapp.ui.theme.AndroidAppTheme

@Composable
fun ToDoListScreen(
    toDos: List<ToDo> = MockDataSource.getToDos(),
    onToDoClick: (ToDo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    text = "ToDo List",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            // Red line
            HorizontalDivider(
                color = Color.Red,
                thickness = 2.dp
            )

            // Scrollable list of ToDos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(toDos) { toDo ->
                    ToDoItemCard(toDo = toDo, onClick = { onToDoClick(toDo) })
                }
            }
        }
    }
}

@Composable
fun ToDoItemCard(toDo: ToDo, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ToDo #${toDo.number}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // Status badge
                StatusBadge(status = toDo.status)
            }

            Text(
                text = toDo.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = toDo.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val assignedText = toDo.assignedToUser?.let { "Assigned: ${it.firstName} ${it.lastName}" } ?: "Unassigned"
                Text(
                    text = assignedText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Remaining: ${toDo.timeRemaining} min",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: Status) {
    val backgroundColor = when (status) {
        Status.NEW -> MaterialTheme.colorScheme.tertiaryContainer
        Status.ASSIGNED -> MaterialTheme.colorScheme.primaryContainer
        Status.FINISHED -> MaterialTheme.colorScheme.secondaryContainer
    }
    val textColor = when (status) {
        Status.NEW -> MaterialTheme.colorScheme.onTertiaryContainer
        Status.ASSIGNED -> MaterialTheme.colorScheme.onPrimaryContainer
        Status.FINISHED -> MaterialTheme.colorScheme.onSecondaryContainer
    }

    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ToDoListScreenPreview() {
    AndroidAppTheme {
        ToDoListScreen()
    }
}
