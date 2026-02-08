package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmrit.campuspulse.Space

@Composable
fun HomeScreen(
    onUpdateClick: () -> Unit,
    onAboutClick: () -> Unit
) {

    val spaces = listOf(
        Space("Library", "Open", 5),
        Space("Canteen", "Limited", 12),
        Space("Academic Block", "Open", 2),
        Space("Admin Office", "Closed", 30),
        Space("Study Area", "Open", 8)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Campus Pulse",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "CMRIT, Bengaluru",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onUpdateClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Space Status")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onAboutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("About Us")
        }

        Spacer(modifier = Modifier.height(24.dp))

        spaces.forEach { space ->

            val statusColor = when (space.status) {
                "Open" -> MaterialTheme.colorScheme.primary
                "Limited" -> MaterialTheme.colorScheme.tertiary
                "Closed" -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.surfaceVariant
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = statusColor.copy(alpha = 0.15f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(
                            text = space.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Updated ${space.lastUpdatedMinutes} mins ago",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = space.status.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        color = statusColor
                    )
                }
            }
        }
    }
}
