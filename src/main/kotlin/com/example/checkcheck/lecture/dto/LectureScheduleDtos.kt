package com.example.checkcheck.lecture.dto

import com.example.checkcheck.common.annotation.ValidEnum
import com.example.checkcheck.common.enums.WeekDay
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class LectureScheduleRequestDto(
    val id: Long? = null,

    @field:NotBlank(message = "강의 시작일을 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-9]{2})-([0-9]{2})-([0-9]{2})\$",
        message = "기간 형식을 확인해주세요! yy-MM-dd"
    )
    @JsonProperty("lectureStartDate")
    val lectureStartDate: String,

    @field:NotBlank(message = "강의 종료일을 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-9]{2})-([0-9]{2})-([0-9]{2})\$",
        message = "기간 형식을 확인해주세요! yy-MM-dd"
    )
    @JsonProperty("lectureEndDate")
    val lectureEndDate: String,

    @field:NotBlank(message = "강의요일을 입력해 주세요.")
    @field:ValidEnum(
        enumClass = WeekDay::class,
        message = "MON, TUE, WED, THU, FRI, SAT, SUN 중 하나를 입력해주세요."
    )
    @JsonProperty("lectureWeekDay")
    val weekDay: WeekDay,

    @field:NotBlank(message = "강의 시작시간을 입력해주세요.")
    @field:Pattern(
        regexp = "^([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("lectureStartAt")
    val lectureStartAt: String,

    @field:NotBlank(message = "강의 종료시간을 입력해 주세요.")
    @field:Pattern(
        regexp = "^([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("lectureEndAt")
    val lectureEndAt: String,

    @field:NotBlank(message = "강의 장소를 입력해 주세요.")
    @JsonProperty("lecturePlace")
    val lecturePlace: String,

    @field:Size(max = 300, message = "강의 정보는 최대 300글자 이내로 작성해 주세요.")
    @JsonProperty("lectureInfo")
    val lectureInfo: String? = null
) {
    val lectureStartAtLocalTime: LocalTime
        get() = lectureStartAt.toLocalTime()

    val lectureEndAtLocalTime: LocalTime
        get() = lectureEndAt.toLocalTime()

    val lectureStartLocalDate: LocalDate
        get() = lectureStartDate.toLocalDate()

    val lectureEndLocalDate: LocalDate
        get() = lectureEndDate.toLocalDate()

    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yy-MM-dd"))

    private fun String.toLocalTime(): LocalTime =
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
}