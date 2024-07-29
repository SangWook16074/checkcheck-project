package com.example.checkcheck.lecture.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class RegisterPeriodRequestDto(
    val id: Long? = null,

    @field:NotBlank(message = "수강신청 시작일을 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-9]{2})-([0-9]{2})-([0-9]{2})\$",
        message = "날짜 형식을 확인해주세요! yy-MM-dd"
    )
    @JsonProperty("registerStartDate")
    private var registerStartDate: String?,

    @field:NotBlank(message = "수강신청 종료일을 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-9]{2})-([0-9]{2})-([0-9]{2})\$",
        message = "날짜 형식을 확인해주세요! yy-MM-dd"
    )
    @JsonProperty("registerEndDate")
    private var registerEndDate: String?,

    @field:NotBlank(message = "수강신청 시작시간을 입력해주세요.")
    @field:Pattern(
        regexp = "^([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("registerStartAt")
    private var registerStartAt: String?,

    @field:NotBlank(message = "수강신청 종료시간을 입력해주세요.")
    @field:Pattern(
        regexp = "^([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("registerEndAt")
    private var registerEndAt: String?
) {
    val startDate: LocalDate
        get() = registerStartDate!!.toLocalDate()

    val endDate: LocalDate
        get() = registerEndDate!!.toLocalDate()

    val startAt: LocalTime
        get() = registerStartAt!!.toLocalTime()

    val endAt: LocalTime
        get() = registerEndAt!!.toLocalTime()

    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yy-MM-dd"))

    private fun String.toLocalTime(): LocalTime =
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
}