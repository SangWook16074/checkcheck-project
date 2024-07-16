package com.example.checkcheck.lecture.dto

import com.example.checkcheck.common.annotation.ValidEnum
import com.example.checkcheck.common.enums.WeekDay
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class LectureScheduleRequestDto(
    val id: Long?,

    @field:NotBlank(message = "강의요일을 입력해 주세요.")
    @field:ValidEnum(
        enumClass = WeekDay::class,
        message = "MON, TUE, WED, THU, FRI, SAT, SUN 중 하나를 입력해주세요."
    )
    @JsonProperty("lectureWeekDay")
    val weekDay: WeekDay,

    @field:NotBlank(message = "강의 시작시간을 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-1]\\d|2[0-4]):([0-5]\\d|60)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("lectureStartAt")
    val startAt: String,

    @field:NotBlank(message = "강의 종료시간을 입력해 주세요.")
    @field:Pattern(
        regexp = "^([0-1]\\d|2[0-4]):([0-5]\\d|60)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("lectureEndAt")
    val endAt: String
)