package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmrit.campuspulse.model.TimetableEntry

@Composable
fun TimetableScreen() {
    val todayClasses = listOf(
        TimetableEntry("Mobile App Development", "09:00 AM - 10:00 AM", "Room 302", "Prof. Sharma"),
        TimetableEntry("Database Management", "10:00 AM - 11:00 AM", "Lab 1", "Dr. Rao"),
        TimetableEntry("Short Break", "11:00 AM - 11:15 AM", "-", "-"),
        TimetableEntry("Computer Networks", "11:15 AM - 12:15 PM", "Room 405", "Prof. Gupta"),
        TimetableEntry("Lunch Break", "12:15 PM - 01:15 PM", "Canteen", "-"),
        TimetableEntry("Machine Learning Lab", "01:15 PM - 03:15 PM", "AI Lab", "Dr. Reddy")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp)
    ) {
        item {
            Text(
                text = "Daily Schedule",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        item {
            Text(
                text = "Monday, 21st Oct",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(todayClasses) { entry ->
            // Identifying status based on the specific demo data
            val isOngoing = entry.time.contains("09:00 AM")
            val isNext = entry.time.contains("10:00 AM")
            val isBreak = entry.subject.contains("Break", ignoreCase = true)
            
            TimetableCard(entry, isOngoing, isNext, isBreak)
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun TimetableCard(entry: TimetableEntry, isOngoing: Boolean, isNext: Boolean, isBreak: Boolean) {
    
    // Unified Status Label
    val statusLabel = when {
        isOngoing -> "ONGOING"
        isNext -> "UP NEXT"
        isBreak -> "BREAK"
        else -> "SCHEDULED"
    }

    // Border and Accent Color based strictly on status
    // Now including a vibrant color for BREAK so it "lights up"
    val accentColor = when {
        isOngoing -> MaterialTheme.colorScheme.primary
        isNext -> MaterialTheme.colorScheme.secondary // Vivid Orange
        isBreak -> Color(0xFF00BFA5) // Modern Vibrant Teal for breaks
        else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    }

    // Unified Card Structure for ALL entries
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(if (isOngoing || isNext || isBreak) 2.dp else 1.dp, accentColor.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Unified Status Chip
                Surface(
                    color = accentColor.copy(alpha = 0.1f),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = statusLabel,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                }

                Text(
                    text = entry.time,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column {
                Text(
                    text = entry.subject,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!isBreak) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = accentColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = entry.room,
                            style = MaterialTheme.typography.bodyMedium,
                            color = accentColor,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "• ${entry.faculty}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        // BREAK content now clearly visible and active
                        Text(
                            text = if (entry.subject.contains("Lunch")) "Main Canteen Area" else "Relax and recharge",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
