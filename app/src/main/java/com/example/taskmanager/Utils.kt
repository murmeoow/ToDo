package com.example.taskmanager

import java.text.SimpleDateFormat
import java.util.Date

class Utils {

    companion object {
        fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat("EEE, MMM, d")
            return formatter.format(date)
        }

    }
}
