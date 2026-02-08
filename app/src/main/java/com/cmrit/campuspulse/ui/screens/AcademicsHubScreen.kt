package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmrit.campuspulse.ui.components.CampusCard

@Composable
fun AcademicsHubScreen(
    onAttendanceClick: () -> Unit,
    onTimetableClick: () -> Unit,
    onNoticesClick: () -> Unit,
    onClassroomsClick: () -> Unit,
    onServicesClick: () -> Unit,
    onMarksClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Academics",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Manage your academic life at CMRIT",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CampusCard(
                title = "Attendance",
                subtitle = "Track your subject-wise attendance",
                icon = Icons.Default.CheckCircle,
                onClick = onAttendanceClick
            )
            CampusCard(
                title = "Timetable",
                subtitle = "View your daily class schedule",
                icon = Icons.Default.DateRange,
                onClick = onTimetableClick
            )
            CampusCard(
                title = "Notices",
                subtitle = "Stay updated with college circulars",
                icon = Icons.Default.Notifications,
                onClick = onNoticesClick
            )
            CampusCard(
                title = "Marks & Results",
                subtitle = "Check your semester performance",
                icon = Icons.Default.Assessment,
                onClick = onMarksClick
            )
            CampusCard(
                title = "Classrooms",
                subtitle = "Check real-time room availability",
                icon = Icons.Default.MeetingRoom,
                onClick = onClassroomsClick
            )
            CampusCard(
                title = "Campus Services",
                subtitle = "Library, transport, and facilities",
                icon = Icons.Default.Widgets,
                onClick = onServicesClick
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}
