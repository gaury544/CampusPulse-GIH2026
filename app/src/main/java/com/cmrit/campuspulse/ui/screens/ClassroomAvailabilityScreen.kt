package com.cmrit.campuspulse.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Calendar

enum class ClassroomStatus {
    Available, Occupied, Reserved
}

data class Classroom(
    val name: String,
    val block: String,
    val capacity: Int,
    val status: ClassroomStatus,
    val nextFreeTime: String? = null,
    val isAfterHoursStudySpace: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassroomAvailabilityScreen() {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val isAfterHours = currentHour < 8 || currentHour >= 18 // After 6 PM and before 8 AM

    val classrooms = listOf(
        Classroom("Room 302", "Academic Block", 60, ClassroomStatus.Available, isAfterHoursStudySpace = false),
        Classroom("Lab 1 (AI)", "Academic Block", 30, ClassroomStatus.Occupied, "02:00 PM", isAfterHoursStudySpace = false),
        Classroom("Seminar Hall A", "Admin Block", 120, ClassroomStatus.Reserved, "04:30 PM", isAfterHoursStudySpace = false),
        Classroom("Room 405", "Academic Block", 60, ClassroomStatus.Available, isAfterHoursStudySpace = false),
        Classroom("Main Library", "Library", 40, ClassroomStatus.Available, isAfterHoursStudySpace = true),
        Classroom("Study Area 1", "Library", 25, ClassroomStatus.Available, isAfterHoursStudySpace = true),
        Classroom("Workshop 2", "Workshop Block", 25, ClassroomStatus.Occupied, "01:00 PM", isAfterHoursStudySpace = false),
        Classroom("Night Lab", "Academic Block", 30, ClassroomStatus.Available, isAfterHoursStudySpace = true)
    )

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Classroom Availability",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        if (isAfterHours) {
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "After-hours mode: Showing available study spaces",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        } else {
            Text(
                text = "Find a place to study or check lab status",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search by room or block...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            shape = MaterialTheme.shapes.medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            val filteredList = classrooms.filter {
                it.name.contains(searchQuery, ignoreCase = true) || 
                it.block.contains(searchQuery, ignoreCase = true)
            }
            
            items(filteredList) { classroom ->
                val isDisabled = isAfterHours && !classroom.isAfterHoursStudySpace
                ClassroomCard(classroom, isDisabled)
            }
        }
    }
}

@Composable
fun ClassroomCard(classroom: Classroom, isDisabled: Boolean = false) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed && !isDisabled) 0.98f else 1f, label = "scale")

    val statusColor = if (isDisabled) {
        MaterialTheme.colorScheme.outline
    } else {
        when (classroom.status) {
            ClassroomStatus.Available -> MaterialTheme.colorScheme.primary
            ClassroomStatus.Occupied -> MaterialTheme.colorScheme.error
            ClassroomStatus.Reserved -> MaterialTheme.colorScheme.tertiary
        }
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                alpha = if (isDisabled) 0.6f else 1f
            }
            .clickable(
                enabled = !isDisabled,
                interactionSource = interactionSource,
                indication = androidx.compose.foundation.LocalIndication.current,
                onClick = { }
            ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = if (isDisabled) 0.dp else 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = statusColor.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.MeetingRoom,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = classroom.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isDisabled) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isDisabled) "Restricted Area" else classroom.block,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = MaterialTheme.shapes.extraSmall,
                    border = androidx.compose.foundation.BorderStroke(1.dp, statusColor.copy(alpha = 0.2f))
                ) {
                    Text(
                        text = if (isDisabled) "CLOSED" else classroom.status.name.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }

            if (!isDisabled) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Capacity: ${classroom.capacity} seats",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    classroom.nextFreeTime?.let { time ->
                        Text(
                            text = "Free at: $time",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}
