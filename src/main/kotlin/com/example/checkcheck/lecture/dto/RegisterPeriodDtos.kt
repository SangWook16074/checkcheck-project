package com.example.checkcheck.lecture.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RegisterPeriodRequestDto(
    val id: Long?,

    @field:NotBlank(message = "수강신청 시작시간을 입력해주세요.")
    @field:Pattern(
        regexp = "^([12]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "시간 형식을 확인해주세요! yyyy-MM-dd HH:mm"
    )
    @JsonProperty("registerStartAt")
    private var _startAt: String?,

    @field:NotBlank(message = "수강신청 종료시간을 입력해주세요.")
    @field:Pattern(
        regexp = "^([12]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "시간 형식을 확인해주세요! yyyy-MM-dd HH:mm"
    )
    @JsonProperty("registerEndAt")
    private var _endAt: String?
) {
    val startAt: LocalDateTime
        get() = _startAt!!.toLocalDateTime()

    val endAt: LocalDateTime
        get() = _endAt!!.toLocalDateTime()

    private fun String.toLocalDateTime(): LocalDateTime =
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}
