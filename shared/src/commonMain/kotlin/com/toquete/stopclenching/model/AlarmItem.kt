package com.toquete.stopclenching.model

import kotlinx.datetime.LocalTime

data class AlarmItem(
    val from: LocalTime,
    val to: LocalTime,
    val intervalMillis: Int
)
