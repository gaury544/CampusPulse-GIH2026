package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmrit.campuspulse.ui.components.CampusCard

data class QuickStat(
    val label: String,
    val value: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val progress: Float? = null,
    val insight: String? = null
)

@Composable
fun HomeScreen(
    onAttendanceClick: () -> Unit = {},
    onTimetableClick: () -> Unit = {},
    onNoticesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onEmergencyClick: () -> Unit = {},
    onActivityClick: () -> Unit = {}
) {
    val stats = listOf(
        QuickStat("Attendance", "76%", Icons.Default.CheckCircle, 0.76f, "↑ 2% from last week"),
        QuickStat("CGPA", "8.42", Icons.Default.Grade, null, "Top 10% of batch"),
        QuickStat("Classes Today", "4 Total", Icons.Default.Event, 0.5f, "2 sessions remaining"),
        QuickStat("New Notices", "2 Unread", Icons.Default.NotificationsActive, null, "Check urgent alerts")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 32.dp)
    ) {
        // Welcome Header
        item(span = { GridItemSpan(2) }) {
            Column {
                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "CMR Institute of Technology",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        item(span = { GridItemSpan(2) }) {
            Text(
                text = "Academic Pulse",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(stats) { stat ->
            QuickStatCard(stat)
        }

        item(span = { GridItemSpan(2) }) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Your Dashboard",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            CampusCard(
                modifier = Modifier.height(110.dp),
                title = "Attendance",
                subtitle = "Track presence",
                icon = Icons.Default.CheckCircle,
                onClick = onAttendanceClick
            )
        }
        item {
            CampusCard(
                modifier = Modifier.height(110.dp),
                title = "Timetable",
                subtitle = "Class schedule",
                icon = Icons.Default.DateRange,
                onClick = onTimetableClick
            )
        }
        item {
            CampusCard(
                modifier = Modifier.height(110.dp),
                title = "Notices",
                subtitle = "Latest updates",
                icon = Icons.Default.Notifications,
                onClick = onNoticesClick
            )
        }
        item {
            CampusCard(
                modifier = Modifier.height(110.dp),
                title = "Activity",
                subtitle = "Campus timeline",
                icon = Icons.Default.History,
                onClick = onActivityClick
            )
        }
        item(span = { GridItemSpan(2) }) {
            CampusCard(
                title = "Emergency",
                subtitle = "Help & Support",
                icon = Icons.Default.Warning,
                iconColor = MaterialTheme.colorScheme.onError,
                iconContainerColor = MaterialTheme.colorScheme.error,
                onClick = onEmergencyClick
            )
        }
    }
}

@Composable
fun QuickStatCard(stat: QuickStat) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth().height(100.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (stat.progress != null) {
                    val color = MaterialTheme.colorScheme.primary
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(32.dp)) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(color = color.copy(alpha = 0.1f), style = Stroke(width = 3.dp.toPx()))
                            drawArc(
                                color = color,
                                startAngle = -90f,
                                sweepAngle = 360f * stat.progress,
                                useCenter = false,
                                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }
                        Icon(stat.icon, null, modifier = Modifier.size(14.dp), tint = color)
                    }
                } else {
                    Icon(stat.icon, null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = stat.value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = stat.label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (stat.insight != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stat.insight, 
                    style = MaterialTheme.typography.labelSmall, 
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1
                )
            }
        }
    }
}
