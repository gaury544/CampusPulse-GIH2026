package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStatusScreen(onBack: () -> Unit) {

    val spaces = listOf(
        "Library",
        "Canteen",
        "Academic Block",
        "Admin Office",
        "Study Area"
    )

    var selectedSpace by remember { mutableStateOf(spaces[0]) }
    var selectedStatus by remember { mutableStateOf("Open") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Enabled scrolling
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Update Space Status",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedSpace,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Space") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                spaces.forEach { space ->
                    DropdownMenuItem(
                        text = { Text(space) },
                        onClick = {
                            selectedSpace = space
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Select Status")

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Open", "Limited", "Closed").forEach { status ->
                Button(
                    onClick = { selectedStatus = status },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedStatus == status)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(status)
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Update")
        }
        
        Spacer(modifier = Modifier.height(48.dp)) // Extra padding at bottom
    }
}
