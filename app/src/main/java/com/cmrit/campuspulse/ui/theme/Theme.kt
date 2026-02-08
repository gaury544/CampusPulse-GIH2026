package com.cmrit.campuspulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CustomDarkColorScheme = darkColorScheme(
    primary = SoftLavender,
    onPrimary = Color(0xFF1A0033),
    primaryContainer = Color(0xFF311B92),
    onPrimaryContainer = SoftLavender,
    
    secondary = VividOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4E342E),
    onSecondaryContainer = VividOrange,
    
    tertiary = AmberAccent,
    
    background = Color(0xFF0F001F), // Neutral dark purple
    onBackground = Color.White,
    
    surface = Color(0xFF1A0033),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D1B4D),
    onSurfaceVariant = SoftLavender,
    
    error = VividOrange,
    outline = RoyalPurple
)

private val CustomLightColorScheme = lightColorScheme(
    primary = RoyalPurple,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE7F6),
    onPrimaryContainer = RoyalPurple,
    
    secondary = VividOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF3E0),
    onSecondaryContainer = VividOrange,
    
    tertiary = AmberAccent,
    
    background = Color(0xFFF8F9FA), // Professional Off-White
    onBackground = Color(0xFF1C1B1F),
    
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF2F2F7),
    onSurfaceVariant = Color(0xFF49454F),
    
    error = VividOrange,
    outline = Color(0xFFCAC4D0)
)

@Composable
fun CampusPulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CustomDarkColorScheme else CustomLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
