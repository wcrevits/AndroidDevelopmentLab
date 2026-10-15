package be.woutcrevits.androidapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import be.woutcrevits.androidapp.R
import be.woutcrevits.androidapp.models.MockDataSource
import be.woutcrevits.androidapp.models.ToDo
import be.woutcrevits.androidapp.ui.theme.AndroidAppTheme

class ToDoPreviewParameterProvider : PreviewParameterProvider<ToDo> {
    private val toDos = MockDataSource.getToDos()
    override val values = toDos.asSequence()
}

@Composable
fun ToDetailScreen(toDo: ToDo, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top row: Image (Icon) + Number + Status
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
                Column {
                    Text(
                        text = "ToDo #${toDo.number}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Status: ${toDo.statusDescription}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Red line
            HorizontalDivider(
                color = Color.Red,
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Title & Description
            Text(
                text = toDo.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = toDo.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Time Estimated & Time Remaining
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Estimated: ${toDo.timeEstimated} min",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Remaining: ${toDo.timeRemaining} min",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom part: enclosed in a border with switches for booleans
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Progress Steps",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    BooleanSwitchRow(label = "Analysis Done", checked = toDo.analysisDone)
                    BooleanSwitchRow(label = "Development Done", checked = toDo.developmentDone)
                    BooleanSwitchRow(label = "Review & Testing Done", checked = toDo.reviewAndTestingDone)
                    BooleanSwitchRow(label = "Acceptance Done", checked = toDo.acceptanceDone)
                }
            }
        }
    }
}

@Composable
fun BooleanSwitchRow(label: String, checked: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked,
            onCheckedChange = { },
            enabled = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview(
    @PreviewParameter(ToDoPreviewParameterProvider::class, limit = 3) toDo: ToDo
) {
    AndroidAppTheme {
        ToDetailScreen(toDo = toDo)
    }
}
