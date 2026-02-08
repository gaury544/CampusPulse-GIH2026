package com.cmrit.campuspulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cmrit.campuspulse.navigation.Screen
import com.cmrit.campuspulse.navigation.bottomNavigationItems
import com.cmrit.campuspulse.ui.screens.*
import com.cmrit.campuspulse.ui.theme.CampusPulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CampusPulseTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CampusPulseApp()
                }
            }
        }
    }
}

@Composable
fun CampusPulseApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigateToTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        bottomBar = {
            val currentRoute = currentDestination?.route
            val isMainTab = bottomNavigationItems.any { it.route == currentRoute }
            val isSubAcademicScreen = listOf(
                Screen.Attendance.route, 
                Screen.Timetable.route, 
                Screen.Notices.route,
                Screen.ClassroomAvailability.route,
                Screen.CampusServices.route,
                Screen.Marks.route,
                Screen.ActivityFeed.route,
                Screen.Emergency.route
            ).contains(currentRoute)
            
            if (isMainTab || isSubAcademicScreen) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    tonalElevation = 8.dp
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp
                    ) {
                        bottomNavigationItems.forEach { screen ->
                            val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                            NavigationBarItem(
                                icon = { 
                                    Icon(
                                        screen.icon, 
                                        contentDescription = screen.title,
                                        tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                                    ) 
                                },
                                label = { 
                                    Text(
                                        screen.title,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                                    ) 
                                },
                                selected = isSelected,
                                onClick = { navigateToTab(screen.route) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    indicatorColor = MaterialTheme.colorScheme.secondary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn(tween(300)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
            exitTransition = { fadeOut(tween(300)) + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300)) },
            popEnterTransition = { fadeIn(tween(300)) + slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) },
            popExitTransition = { fadeOut(tween(300)) + slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300)) }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onAttendanceClick = { navigateToTab(Screen.Attendance.route) },
                    onTimetableClick = { navigateToTab(Screen.Timetable.route) },
                    onNoticesClick = { navigateToTab(Screen.Notices.route) },
                    onProfileClick = { navigateToTab(Screen.Profile.route) },
                    onEmergencyClick = { navController.navigate(Screen.Emergency.route) },
                    onActivityClick = { navigateToTab(Screen.ActivityFeed.route) }
                )
            }
            composable(Screen.Academics.route) {
                AcademicsHubScreen(
                    onAttendanceClick = { navController.navigate(Screen.Attendance.route) },
                    onTimetableClick = { navController.navigate(Screen.Timetable.route) },
                    onNoticesClick = { navController.navigate(Screen.Notices.route) },
                    onClassroomsClick = { navController.navigate(Screen.ClassroomAvailability.route) },
                    onServicesClick = { navController.navigate(Screen.CampusServices.route) },
                    onMarksClick = { navController.navigate(Screen.Marks.route) }
                )
            }
            composable(Screen.Attendance.route) {
                AttendanceScreen()
            }
            composable(Screen.Timetable.route) {
                TimetableScreen()
            }
            composable(Screen.Notices.route) {
                NoticesScreen()
            }
            composable(Screen.ClassroomAvailability.route) {
                ClassroomAvailabilityScreen()
            }
            composable(Screen.CampusServices.route) {
                CampusServicesScreen()
            }
            composable(Screen.Marks.route) {
                MarksScreen()
            }
            composable(Screen.ActivityFeed.route) {
                ActivityFeedScreen()
            }
            composable(Screen.Map.route) {
                CampusMapScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
            composable(Screen.Emergency.route) {
                EmergencyScreen(
                    onNavigateToMap = { buildingId ->
                        navigateToTab(Screen.Map.route)
                    }
                )
            }
            composable(Screen.About.route) {
                AboutUsScreen(onBack = { navController.popBackStack() })
            }
            composable("update") {
                UpdateStatusScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
