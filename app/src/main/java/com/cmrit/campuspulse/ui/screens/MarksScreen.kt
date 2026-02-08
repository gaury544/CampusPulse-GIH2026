package com.cmrit.campuspulse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class SubjectResult(
    val subject: String,
    val marks: Int,
    val total: Int,
    val grade: String,
    val attendance: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarksScreen() {
    val semesters = listOf("Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6")
    var selectedSemester by remember { mutableStateOf(semesters.last()) }
    var expanded by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Mock data with correlated attendance
    val semesterResults = remember(selectedSemester) {
        when (selectedSemester) {
            "Semester 6" -> listOf(
                SubjectResult("Mobile App Development", 88, 100, "A+", 92f),
                SubjectResult("Software Engineering", 75, 100, "A", 85f),
                SubjectResult("Computer Networks", 82, 100, "A", 88f),
                SubjectResult("Database Management", 68, 100, "B+", 72f),
                SubjectResult("Operating Systems", 91, 100, "O", 95f),
                SubjectResult("Machine Learning", 72, 100, "B+", 78f)
            )
            "Semester 5" -> listOf(
                SubjectResult("Java Programming", 85, 100, "A", 90f),
                SubjectResult("Cloud Computing", 70, 100, "B+", 75f),
                SubjectResult("Web Tech", 92, 100, "O", 96f),
                SubjectResult("Cyber Security", 78, 100, "A", 82f)
            )
            else -> listOf(
                SubjectResult("Core Subject 1", 80, 100, "A", 85f),
                SubjectResult("Core Subject 2", 85, 100, "A+", 88f),
                SubjectResult("Lab Work", 95, 100, "O", 98f)
            )
        }
    }

    val totalObtained = semesterResults.sumOf { it.marks }
    val totalPossible = semesterResults.sumOf { it.total }
    val currentSGPA = if (totalPossible > 0) (totalObtained.toFloat() / totalPossible.toFloat()) * 10 else 0f
    
    val cgpa = 8.42f
    val previousSGPA = 7.85f
    val hasImproved = currentSGPA >= previousSGPA

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Academic Results",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { 
                    scope.launch {
                        snackbarHostState.showSnackbar("Generating full academic report...")
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download Report",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedSemester,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Semester") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    semesters.forEach { semester ->
                        DropdownMenuItem(
                            text = { Text(semester) },
                            onClick = {
                                selectedSemester = semester
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoGraph,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Smart Insight: Higher attendance strongly correlates with your better performance this semester.",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PerformanceMetricCard(
                    title = "Semester GPA",
                    value = String.format("%.2f", currentSGPA),
                    subtitle = "Current",
                    modifier = Modifier.weight(1f),
                    indicatorIcon = if (hasImproved) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                    indicatorColor = if (hasImproved) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                PerformanceMetricCard(
                    title = "Cumulative GPA",
                    value = String.format("%.2f", cgpa),
                    subtitle = "Total",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Correlated Performance Breakdown",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(semesterResults) { result ->
                    CorrelatedResultCard(result)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { 
                    scope.launch {
                        snackbarHostState.showSnackbar("Downloading ${selectedSemester} analytics report...")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Download Analytics Report")
            }
        }
    }
}

@Composable
fun CorrelatedResultCard(result: SubjectResult) {
    val marksPercentage = (result.marks.toFloat() / result.total.toFloat())
    val performanceColor = when {
        marksPercentage >= 0.85f -> Color(0xFF4CAF50)
        marksPercentage >= 0.65f -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = result.subject,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Marks: ${result.marks}/${result.total}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Attendance: ${result.attendance.toInt()}%",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = if (result.attendance >= 75f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                }
                
                Surface(
                    color = performanceColor.copy(alpha = 0.1f),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, performanceColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = result.grade,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = performanceColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = marksPercentage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = performanceColor,
                trackColor = performanceColor.copy(alpha = 0.1f)
            )
        }
    }
}

@Composable
fun PerformanceMetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    indicatorIcon: ImageVector? = null,
    indicatorColor: Color = Color.Unspecified
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                if (indicatorIcon != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = indicatorIcon, contentDescription = null, tint = indicatorColor, modifier = Modifier.size(20.dp))
                }
            }
            Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
