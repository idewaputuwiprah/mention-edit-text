package com.adit.library

data class MentionSpan(
    val mention: String,
    val start: Int,
    val end: Int
)