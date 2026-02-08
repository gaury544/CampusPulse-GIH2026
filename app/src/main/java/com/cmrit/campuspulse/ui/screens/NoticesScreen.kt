package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class NoticePriority {
    URGENT, IMPORTANT, INFO
}

data class NoticeItem(
    val id: Int,
    val title: String,
    val date: String,
    val content: String,
    val priority: NoticePriority
)

@Composable
fun NoticesScreen() {
    val notices = listOf(
        NoticeItem(1, "End Semester Exams Schedule", "Oct 20, 2024", "The final timetable for the end semester examinations has been released. Check the notice board for detailed room allotments.", NoticePriority.URGENT),
        NoticeItem(2, "Annual Cultural Fest 2024", "Oct 25, 2024", "Join us for a week of cultural activities, music, and dance starting from Oct 25th in the main auditorium.", NoticePriority.IMPORTANT),
        NoticeItem(3, "Placement Drive: TechCorp", "Oct 18, 2024", "TechCorp is visiting CMRIT for a placement drive for final year students. Mandatory attendance for registered candidates.", NoticePriority.URGENT),
        NoticeItem(4, "Library Timing Update", "Oct 15, 2024", "The library will now remain open until 8:00 PM on weekdays for exam preparation.", NoticePriority.INFO),
        NoticeItem(5, "Guest Lecture on AI/ML", "Oct 12, 2024", "A guest lecture by industry experts on the latest trends in AI and Machine Learning in Seminar Hall 1.", NoticePriority.IMPORTANT),
        NoticeItem(6, "Holiday Announcement", "Nov 01, 2024", "The college will remain closed on Nov 1st on account of Karnataka Rajyotsava.", NoticePriority.INFO)
    )

    // Sort notices by priority: URGENT first, then IMPORTANT, then INFO
    val sortedNotices = notices.sortedBy { it.priority }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Campus Notices",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Stay informed with official CMRIT updates",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(sortedNotices) { notice ->
                NoticeCard(notice)
            }
        }
    }
}

@Composable
fun NoticeCard(notice: NoticeItem) {
    val (priorityColor, priorityIcon) = when (notice.priority) {
        NoticePriority.URGENT -> MaterialTheme.colorScheme.error to Icons.Default.NewReleases
        NoticePriority.IMPORTANT -> MaterialTheme.colorScheme.tertiary to Icons.Default.PushPin
        NoticePriority.INFO -> MaterialTheme.colorScheme.primary to Icons.Default.Info
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
        tonalElevation = if (notice.priority == NoticePriority.URGENT) 8.dp else 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Priority Badge
                Surface(
                    color = priorityColor.copy(alpha = 0.1f),
                    shape = MaterialTheme.shapes.extraSmall,
                    border = BorderStroke(1.dp, priorityColor.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = priorityIcon,
                            contentDescription = null,
                            tint = priorityColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = notice.priority.name,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = priorityColor
                        )
                    }
                }

                Text(
                    text = notice.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = notice.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (notice.priority == NoticePriority.URGENT) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = notice.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }
}
