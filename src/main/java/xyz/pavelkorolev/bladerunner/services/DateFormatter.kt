package xyz.pavelkorolev.bladerunner.services

import java.text.SimpleDateFormat
import java.util.*

/**
 * Service for date formatting
 */
interface DateFormatter {

    /**
     * Formats given date
     */
    fun formatDate(date: Date): String

}

class DateFormatterImpl : DateFormatter {

    private val formatter by lazy { SimpleDateFormat("yyyy-MM-dd_HH-mm-ss") }

    override fun formatDate(date: Date): String =
        formatter.format(date)

}