package com.cmrit.campuspulse.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class CampusBuilding(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val xPercent: Float,
    val yPercent: Float
)

data class LocationMarkerData(
    val label: String,
    val icon: ImageVector,
    val xPercent: Float,
    val yPercent: Float
)

@Composable
fun CampusMapScreen() {
    val buildings = remember {
        listOf(
            CampusBuilding(0, "Academic Block", Icons.Default.School, 0.5f, 0.12f),
            CampusBuilding(1, "Library", Icons.Default.MenuBook, 0.15f, 0.4f),
            CampusBuilding(2, "Admin Block", Icons.Default.Business, 0.85f, 0.4f),
            CampusBuilding(3, "Canteen", Icons.Default.Restaurant, 0.3f, 0.7f),
            CampusBuilding(4, "Hostel", Icons.Default.Bed, 0.1f, 0.85f),
            CampusBuilding(5, "Parking", Icons.Default.LocalParking, 0.85f, 0.12f),
            CampusBuilding(6, "Sports Ground", Icons.Default.SportsSoccer, 0.9f, 0.8f),
            CampusBuilding(7, "Auditorium", Icons.Default.TheaterComedy, 0.5f, 0.45f),
            CampusBuilding(8, "Main Gate", Icons.Default.Login, 0.5f, 0.92f),
            CampusBuilding(9, "Placement Cell", Icons.Default.Work, 0.75f, 0.28f),
            CampusBuilding(10, "Workshop", Icons.Default.Build, 0.25f, 0.28f),
            CampusBuilding(11, "Seminar Hall", Icons.Default.Groups, 0.7f, 0.6f)
        )
    }

    val paths = remember {
        listOf(
            Pair(8, 3), Pair(3, 4), Pair(8, 6), Pair(6, 2), Pair(4, 1),
            Pair(1, 7), Pair(2, 7), Pair(3, 7), Pair(7, 0), Pair(0, 5),
            Pair(2, 5), Pair(1, 10), Pair(10, 0), Pair(0, 9), Pair(9, 2),
            Pair(2, 11), Pair(11, 6), Pair(7, 11)
        )
    }

    val youAreHereLocation = remember {
        LocationMarkerData("You are here", Icons.Filled.MyLocation, 0.4f, 0.88f)
    }

    var selectedBuildingId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Campus Navigator",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        val subtitle = selectedBuildingId?.let { id ->
            val buildingName = buildings.find { b -> b.id == id }?.name
            "Showing paths for $buildingName"
        } ?: "Tap a building to see connections"
        
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Glass Schematic Map Area
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
            tonalElevation = 8.dp
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { 
                        selectedBuildingId = null
                    }
                    .padding(16.dp)
            ) {
                val widthDp = maxWidth
                val heightDp = maxHeight

                val pathColor = MaterialTheme.colorScheme.outlineVariant
                val highlightColor = MaterialTheme.colorScheme.primary
                
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    
                    // Subtle blueprint grid
                    val gridSpacing = 40.dp.toPx()
                    for (x in 0..(canvasWidth / gridSpacing).toInt()) {
                        drawLine(color = pathColor.copy(alpha = 0.1f), start = Offset(x * gridSpacing, 0f), end = Offset(x * gridSpacing, canvasHeight), strokeWidth = 1f)
                    }
                    for (y in 0..(canvasHeight / gridSpacing).toInt()) {
                        drawLine(color = pathColor.copy(alpha = 0.1f), start = Offset(0f, y * gridSpacing), end = Offset(canvasWidth, y * gridSpacing), strokeWidth = 1f)
                    }

                    paths.forEach { (startIndex, endIndex) ->
                        val isHighlighted = selectedBuildingId == startIndex || selectedBuildingId == endIndex
                        
                        val start = buildings[startIndex]
                        val end = buildings[endIndex]
                        
                        // Path animations logic handled within Canvas draws
                        drawLine(
                            color = if (isHighlighted) highlightColor else pathColor,
                            start = Offset(start.xPercent * canvasWidth, start.yPercent * canvasHeight),
                            end = Offset(end.xPercent * canvasWidth, end.yPercent * canvasHeight),
                            strokeWidth = if (isHighlighted) 6.dp.toPx() else 4.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }
                }

                buildings.forEach { building ->
                    val isSelected = building.id == selectedBuildingId
                    BuildingMarker(
                        building = building,
                        isSelected = isSelected,
                        onClick = { selectedBuildingId = building.id },
                        modifier = Modifier.offset(
                            x = (widthDp * building.xPercent) - 24.dp,
                            y = (heightDp * building.yPercent) - 24.dp
                        )
                    )
                }

                YouAreHereMarker(
                    data = youAreHereLocation,
                    modifier = Modifier.offset(
                        x = (widthDp * youAreHereLocation.xPercent) - 24.dp,
                        y = (heightDp * youAreHereLocation.yPercent) - 24.dp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Legend Section
        Text(
            text = "Legend",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        val legendItems = buildings + CampusBuilding(-1, "You are here", Icons.Filled.MyLocation, 0f, 0f)
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(150.dp)
        ) {
            items(legendItems) { item ->
                LegendItem(item)
            }
        }
    }
}

@Composable
fun LegendItem(building: CampusBuilding) {
    val isUserLocation = building.id == -1
    Surface(
        color = if (isUserLocation) MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f) 
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = building.icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isUserLocation) MaterialTheme.colorScheme.onTertiaryContainer 
                       else MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = building.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
        }
    }
}

@Composable
fun BuildingMarker(
    building: CampusBuilding,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Animate size and elevation
    val size by animateDpAsState(if (isSelected) 56.dp else 48.dp, label = "size")
    val elevation by animateDpAsState(if (isSelected) 8.dp else 2.dp, label = "elevation")
    val iconSize by animateDpAsState(if (isSelected) 28.dp else 24.dp, label = "iconSize")
    
    // Animate colors
    val containerColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.primary 
        else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f),
        label = "color"
    )
    val contentColor by animateColorAsState(
        if (isSelected) MaterialTheme.colorScheme.onPrimary 
        else MaterialTheme.colorScheme.onSecondaryContainer,
        label = "contentColor"
    )

    Column(
        modifier = modifier.clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = containerColor,
            tonalElevation = elevation,
            modifier = Modifier.size(size),
            border = if (isSelected) null else BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = building.icon,
                    contentDescription = building.name,
                    tint = contentColor,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
            shape = MaterialTheme.shapes.extraSmall,
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.3f))
        ) {
            Text(
                text = building.name,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun YouAreHereMarker(
    data: LocationMarkerData,
    modifier: Modifier = Modifier
) {
    // Initial entry animation for the "You are here" marker
    var startAnim by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(if (startAnim) 1f else 0f, animationSpec = tween(1000), label = "alpha")
    
    LaunchedEffect(Unit) {
        startAnim = true
    }

    Column(
        modifier = modifier.background(Color.Transparent).graphicsLayer { this.alpha = alpha },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f),
            tonalElevation = 8.dp,
            modifier = Modifier.size(48.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = data.icon,
                    contentDescription = data.label,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.8f),
            shape = MaterialTheme.shapes.extraSmall,
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.4f))
        ) {
            Text(
                text = data.label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}
