package com.example.checkcheck.lecture.entity

import com.example.checkcheck.lecture.dto.LectureResponseDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,

    //강의명
    @Column(nullable = false, updatable = false, length = 50)
    var title: String,

    //강사명
    @Column(nullable = false, updatable = false, length = 50)
    var name: String,

    //강의시간
    @Column(nullable = false, updatable = false, length = 50)
    var time: String,

    //수강신청기간
    @Column(nullable = false, updatable = false, length = 50)
    var period: String,

    //강의정보
    @Column(nullable = false, updatable = false, length = 200)
    var information: String
) {
    fun toResponse() : LectureResponseDto = LectureResponseDto(
        title = title,
        name = name,
        time = time,
        period = period,
        information = information
    )
}