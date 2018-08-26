package com.joshtalks.task.common

import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun getKMGFormat(value: Double): String {
        if (value < 1000) return "" + value
        val exp = (Math.log(value) / Math.log(1000.0)).toInt()
        return String.format("%.1f %c",
                value / Math.pow(1000.0, exp.toDouble()),
                "kMGTPE"[exp - 1])
    }

    fun getFormatedDate(value: Long):String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis =value
        return formatter.format(calendar.time)
    }
}