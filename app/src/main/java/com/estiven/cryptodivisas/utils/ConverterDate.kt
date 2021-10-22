package com.estiven.cryptodivisas.utils

import java.text.SimpleDateFormat
import java.util.*

object ConverterDate {
    fun date(date: String): Date? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.parse(date)
    }
}