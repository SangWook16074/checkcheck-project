package com.example.checkcheck.lecture.entity

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.dto.LectureResponseDto
import jakarta.persistence.*
import java.time.LocalTime

@Entity
class LectureSchedule (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long?,

    @Column
    @Enumerated(EnumType.STRING)
    var weekDay : WeekDay,

    @Column
    @Temporal(TemporalType.TIME)
    var startAt : LocalTime,

    @Column
    @Temporal(TemporalType.TIME)
    var endAt : LocalTime,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_schedule_lecture_id"))
    var lecture: Lecture

) {
    fun toResponse() = LectureResponseDto(
        title = lecture.title,
        maxStudent = lecture.maxStudent,
        resisterPeriod = lecture.resisterPeriod,
        lectureSchedule = this, //현재 객체를 할당 (현재 객체 = lectureSchedule)
        member = lecture.member
    )
}
