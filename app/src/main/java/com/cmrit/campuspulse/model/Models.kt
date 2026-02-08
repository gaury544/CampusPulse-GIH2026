package com.cmrit.campuspulse.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Attendance(
    val subjectName: String,
    val attended: Int,
    val total: Int
) {
    val percentage: Float
        get() = if (total > 0) (attended.toFloat() / total.toFloat()) * 100 else 0f
}

data class TimetableEntry(
    val subject: String,
    val time: String,
    val room: String,
    val faculty: String
)

data class Notice(
    val id: Int,
    val title: String,
    val date: String,
    val content: String,
    val isUrgent: Boolean = false
)
