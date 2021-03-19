package com.example.shoppingapplication.util

import java.text.SimpleDateFormat

class Date {
    companion object {
        fun getCurrentSystemTime(systemTime: Long): String {
            return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
                    .format(systemTime).toString()
        }
    }
}