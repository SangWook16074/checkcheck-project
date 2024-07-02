package com.example.checkcheck.lecture.dto

import com.example.checkcheck.lecture.entity.Lecture
import jakarta.validation.constraints.NotBlank

data class LectureRequestDto(
    @field:NotBlank(message = "강의명을 입력해 주시길 바랍니다.")
    var title: String,

    @field:NotBlank(message = "강사명을 입력해 주시길 바랍니다.")
    val name : String,

    @field:NotBlank(message = "강의시간을 입력해 주시길 바랍니다.")
    var time: String,

    @field:NotBlank(message = "수강신청기간을 입력해 주시길 바랍니다.")
    var period: String,

    @field:NotBlank(message = "강의정보를 입력해 주시길 바랍니다.")
    var information: String
) {
    fun toEntity(): Lecture = Lecture(
        id = null,
        title = title,
        name = name,
        time = time,
        period = period,
        information = information
    )
}

data class LectureResponseDto(
    var title: String,
    var name: String,
    var time: String,
    var period: String,
    var information: String
)
