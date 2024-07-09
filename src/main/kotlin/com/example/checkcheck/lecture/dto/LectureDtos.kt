package com.example.checkcheck.lecture.dto

import com.example.checkcheck.common.annotation.ValidEnum
import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.LectureSchedule
import com.example.checkcheck.lecture.entity.ResisterPeriod
import com.example.checkcheck.member.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class LectureRequestDto(
    @field:NotBlank(message = "강의명을 입력해 주세요.")
    @JsonProperty("title")
    private var _title: String?,


    @field:NotBlank(message = "강의시작시간을 입력해 주세요.")
    @field:Pattern(
        regexp = "^([12]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01]\\d|2[0-4]):([0-5]\\d|60)\$",
        message = "시간 형식을 확인해주세요! yyyy-MM-dd HH:mm"
    )
    @JsonProperty("resisterStartAt")
    private var _resisterStartAt : String?,


    @field:NotBlank(message = "강의종료시간을 입력해 주세요.")
    @field:Pattern(
        regexp = "^([12]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01]\\d|2[0-4]):([0-5]\\d|60)\$",
        message = "시간 형식을 확인해주세요! yyyy-MM-dd HH:mm"
    )
    @JsonProperty("resisterEndAt")
    private var _resisterEndAt: String?,


    @field:NotBlank(message = "수강신청 시작시간을 입력해 주세요.")
    @field:Pattern(
        regexp = "^([0-1]\\d|2[0-4]):([0-5]\\d|60)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("lectureStartAt")
    private var _lectureStartAt: String?,


    @field:NotBlank(message = "수강신청 종료시간을 입력해 주세요.")
    @field:Pattern(
        regexp = "^([0-1]\\d|2[0-4]):([0-5]\\d|60)\$",
        message = "시간 형식을 확인해주세요! HH:mm"
    )
    @JsonProperty("lectureEndAt")
    private var _lectureEndAt: String?,


    @field:NotBlank(message = "강의요일을 입력해 주세요.")
    @field:ValidEnum(
        enumClass = WeekDay::class,
        message = "MON, TUE, WED, THU, FRI, SAT, SUN 중 하나를 입력해주세요."
    )
    @JsonProperty("lectureWeekDay")
    private var _lectureWeekDay : String?,


    @field:NotNull(message = "최대 수강 학생을 입력해주세요.")
    @JsonProperty("maxStudent")
    private var _maxStudent : Int?
) {

    val title : String
        get() = _title!!

    val resisterStartAt : LocalDateTime
        get() = _resisterStartAt!!.toLocalDateTime()

    val resisterEndAt : LocalDateTime
        get() = _resisterEndAt!!.toLocalDateTime()

    val lectureStartAt : LocalTime
        get() = _lectureStartAt!!.toLocalTime()

    val lectureEndAt : LocalTime
        get() = _lectureEndAt!!.toLocalTime()

    val lectureWeekDay : WeekDay
        get() = WeekDay.valueOf(_lectureWeekDay!!)

    val maxStudent : Int
        get() = _maxStudent!!

    private fun String.toLocalTime() : LocalTime =
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))

    private fun String.toLocalDateTime() : LocalDateTime =
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}

data class LectureResponseDto(
    var title: String,
    var maxStudent : Int,
    var resisterPeriod: ResisterPeriod?,
    var lectureSchedule: LectureSchedule?,
    var member: Member?,
)
