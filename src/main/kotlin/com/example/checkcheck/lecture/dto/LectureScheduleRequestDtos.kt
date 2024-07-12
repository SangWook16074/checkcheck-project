package com.example.checkcheck.lecture.dto

import com.example.checkcheck.common.enums.WeekDay
import java.time.LocalTime

data class LectureScheduleRequestDto(
    val id: Long?,
    val weekDay: WeekDay,
    val startAt: LocalTime,
    val endAt: LocalTime
)
