package com.example.checkcheck.lecture.dto

import java.time.LocalDateTime

data class RegisterPeriodRequestDto(
    val id: Long?,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)
