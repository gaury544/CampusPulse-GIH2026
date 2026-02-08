package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CommentBank
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ActivityItem(
    val title: String,
    val description: String,
    val time: String,
    val icon: ImageVector,
    val category: String
)

@Composable
fun ActivityFeedScreen() {
    val activities = listOf(
        ActivityItem(
            "Attendance Updated",
            "Your attendance for 'Computer Networks' was marked: Present.",
            "10:30 AM",
            Icons.Default.FactCheck,
            "Academic"
        ),
        ActivityItem(
            "New Notice",
            "Final Lab Exam schedule for 6th Sem has been posted.",
            "09:15 AM",
            Icons.Default.NotificationsActive,
            "Notice"
        ),
        ActivityItem(
            "Library Status",
            "Main Library occupancy is currently Low (25%).",
            "08:45 AM",
            Icons.Default.MeetingRoom,
            "Campus"
        ),
        ActivityItem(
            "Marks Posted",
            "Internal Assessment marks for 'Software Engineering' are out.",
            "Yesterday",
            Icons.Default.CommentBank,
            "Academic"
        ),
        ActivityItem(
            "Canteen Update",
            "Special lunch menu available today at Main Canteen.",
            "Yesterday",
            Icons.Default.NotificationsActive,
            "Events"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Campus Activity",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Stay updated with real-time campus events",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp) // Timeline look needs items to touch
        ) {
            items(activities) { activity ->
                ActivityTimelineItem(activity)
            }
        }
    }
}

@Composable
fun ActivityTimelineItem(activity: ActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Important for the vertical line to span correctly
    ) {
        // Timeline Column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(48.dp)
        ) {
            // Top part of line
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .weight(0.1f)
                    .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            )
            
            // Icon / Dot
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = activity.icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            // Bottom part of line
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .weight(0.9f)
                    .background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
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
                    text = activity.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = activity.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = activity.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "#${activity.category}",
                        style = MaterialTheme.typography.labelExtraSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

// Added missing typography style for category tag
val Typography.labelExtraSmall: androidx.compose.ui.text.TextStyle
    @Composable
    get() = labelSmall.copy(fontSize = 10.sp)
