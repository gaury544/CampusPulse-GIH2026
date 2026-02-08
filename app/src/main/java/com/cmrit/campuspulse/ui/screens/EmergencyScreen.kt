package com.cmrit.campuspulse.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cmrit.campuspulse.ui.components.CampusCard

data class EmergencyContact(
    val title: String,
    val subtitle: String,
    val number: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconColor: Color
)

@Composable
fun EmergencyScreen(
    onNavigateToMap: (buildingId: Int) -> Unit = {}
) {
    val contacts = listOf(
        EmergencyContact("Campus Security", "Main Gate & 24/7 Patrol", "+91 80 2852 4466", Icons.Default.Security, MaterialTheme.colorScheme.primary),
        EmergencyContact("Medical Center", "Ambulance & First Aid", "+91 80 2852 4467", Icons.Default.MedicalServices, MaterialTheme.colorScheme.error),
        EmergencyContact("Fire Emergency", "On-campus Fire Safety", "101", Icons.Default.FireTruck, Color(0xFFE65100)),
        EmergencyContact("Hostel Warden", "Boys & Girls Hostel Support", "+91 80 2852 4468", Icons.Default.HomeWork, MaterialTheme.colorScheme.secondary)
    )

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Emergency & Help",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Immediate assistance for CMRIT campus",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Warning Notice
        item {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.2f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ReportProblem, null, tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "In case of life-threatening emergencies, call campus security immediately.",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }

        item {
            Column {
                Text(
                    text = "Quick Navigation",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AssistChip(
                        onClick = { onNavigateToMap(7) },
                        label = { Text("Medical Center") },
                        leadingIcon = { Icon(Icons.Default.LocalHospital, null, Modifier.size(18.dp)) }
                    )
                    AssistChip(
                        onClick = { onNavigateToMap(8) },
                        label = { Text("Emergency Exit") },
                        leadingIcon = { Icon(Icons.Default.ExitToApp, null, Modifier.size(18.dp)) }
                    )
                }
            }
        }

        items(contacts) { contact ->
            CampusCard(
                title = contact.title,
                subtitle = "${contact.subtitle}\n${contact.number}",
                icon = contact.icon,
                iconColor = contact.iconColor,
                iconContainerColor = contact.iconColor.copy(alpha = 0.1f),
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${contact.number}")
                    }
                    context.startActivity(intent)
                }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
