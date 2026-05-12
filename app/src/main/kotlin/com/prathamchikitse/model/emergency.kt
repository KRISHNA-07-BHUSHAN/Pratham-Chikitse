package com.prathamchikitse.model

data class Emergency(
    val id: String,
    val title: String,
    val emoji: String,
    val colorHex: String,
    val steps: List<String> = emptyList(),
    val dos: List<String> = emptyList(),
    val donts: List<String> = emptyList()
)