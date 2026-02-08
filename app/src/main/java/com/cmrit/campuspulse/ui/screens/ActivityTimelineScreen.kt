package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class EventType {
    ATTENDANCE, NOTICE, SPACE
}

data class TimelineEvent(
    val title: String,
    val description: String,
    val timestamp: String,
    val type: EventType
)

@Composable
fun ActivityTimelineScreen() {
    val timelineEvents = listOf(
        TimelineEvent(
            "Attendance Updated",
            "Your attendance for Mobile App Development was marked: Present.",
            "10:45 AM",
            EventType.ATTENDANCE
        ),
        TimelineEvent(
            "New Notice",
            "Important: Mid-semester marks have been uploaded to the portal.",
            "09:30 AM",
            EventType.NOTICE
        ),
        TimelineEvent(
            "Space Availability",
            "Study Area 1 in the Library is now Available.",
            "08:15 AM",
            EventType.SPACE
        ),
        TimelineEvent(
            "Classroom Update",
            "Room 302 is now Occupied for a Guest Lecture.",
            "Yesterday",
            EventType.SPACE
        ),
        TimelineEvent(
            "Attendance Alert",
            "Attendance for Machine Learning Lab marked: Absent.",
            "Yesterday",
            EventType.ATTENDANCE
        ),
        TimelineEvent(
            "Event Reminder",
            "Cultural Fest registrations close this evening.",
            "Yesterday",
            EventType.NOTICE
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Activity Timeline",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Latest updates from around the CMRIT campus",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(0.dp), // Using 0 to connect the timeline line
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(timelineEvents) { event ->
                TimelineEventItem(event)
            }
        }
    }
}

@Composable
fun TimelineEventItem(event: TimelineEvent) {
    val (icon, color) = when (event.type) {
        EventType.ATTENDANCE -> Icons.Default.CheckCircle to MaterialTheme.colorScheme.primary
        EventType.NOTICE -> Icons.Default.Notifications to MaterialTheme.colorScheme.tertiary
        EventType.SPACE -> Icons.Default.MeetingRoom to MaterialTheme.colorScheme.secondary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Timeline indicator column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.1f),
                border = BorderStroke(1.dp, color.copy(alpha = 0.5f)),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            // Vertical line connecting events
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .padding(vertical = 4.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content Column
        Column(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = event.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
                tonalElevation = 1.dp
            ) {
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(12.dp),
                    lineHeight = 18.sp
                )
            }
        }
    }
}
