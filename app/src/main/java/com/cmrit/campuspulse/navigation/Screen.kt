package com.cmrit.campuspulse.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Academics : Screen("academics", "Academics", Icons.Default.School)
    object Map : Screen("map", "Map", Icons.Default.Map)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    
    // Sub-screens
    object Attendance : Screen("attendance", "Attendance", Icons.Default.CheckCircle)
    object Timetable : Screen("timetable", "Timetable", Icons.Default.DateRange)
    object Notices : Screen("notices", "Notices", Icons.Default.Notifications)
    object ClassroomAvailability : Screen("classrooms", "Classrooms", Icons.Default.MeetingRoom)
    object CampusServices : Screen("services", "Services", Icons.Default.Widgets)
    object Marks : Screen("marks", "Marks", Icons.Default.Assessment)
    object ActivityFeed : Screen("activity", "Activity", Icons.Default.History)
    object Emergency : Screen("emergency", "Emergency", Icons.Default.Warning)
    object About : Screen("about", "About Us", Icons.Default.Info)
}

val bottomNavigationItems = listOf(
    Screen.Home,
    Screen.Academics,
    Screen.Map,
    Screen.Profile
)
