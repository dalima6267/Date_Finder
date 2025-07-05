package com.example.datefinder

object DateExtractor {
    private val patterns = listOf(
        "\\b\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}\\b",        // 12/10/2023
        "\\b\\d{4}[/-]\\d{1,2}[/-]\\d{1,2}\\b",          // 2023-10-12
        "\\b\\d{1,2}\\s+[A-Za-z]{3,9}\\s+\\d{4}\\b",     // 12 October 2023
        "\\b[A-Za-z]{3,9}\\s+\\d{1,2},\\s+\\d{4}\\b"    // 12 October 2023
    )
    fun extractRelevantDate(text: String): String? {
        for (pattern in patterns) {
            val match = Regex(pattern).find(text)
            if (match != null) return match.value
        }
        return null
    }
}