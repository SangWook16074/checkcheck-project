package com.example.checkcheck.lecture.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RegisterPeriodRequestDto(
    val id: Long? = null,

    @field:NotBlank(message = "수강신청 시작 일정를 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-9]{2})-([0-9]{2})-([0-9]{2}) ([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "형식을 확인해주세요! yy-MM-dd HH:mm"
    )
    @JsonProperty("registerStartDateTime")
    val registerStartDateTime: String,

    @field:NotBlank(message = "수강신청 종료 일정를 입력해주세요.")
    @field:Pattern(
        regexp = "^([0-9]{2})-([0-9]{2})-([0-9]{2}) ([01]\\d|2[0-3]):([0-5]\\d)\$",
        message = "형식을 확인해주세요! yy-MM-dd HH:mm"
    )
    @JsonProperty("registerEndDateTime")
    val registerEndDateTime: String
) {
    val registerStartLocalDateTime: LocalDateTime
        get() = registerStartDateTime.toLocalDateTime()

    val registerEndLocalDateTime: LocalDateTime
        get() = registerEndDateTime.toLocalDateTime()


    private fun String.toLocalDateTime(): LocalDateTime =
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"))
}