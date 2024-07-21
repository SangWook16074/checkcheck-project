package com.example.checkcheck.lecture.entity

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.dto.LectureScheduleRequestDto
import jakarta.persistence.*

@Entity
class LectureSchedule (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long?,

    @Column
    @Enumerated(EnumType.STRING)
    var weekDay : WeekDay,

    @Column
    var startAt : String,

    @Column
    var endAt : String,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_schedule_lecture_id"))
    var lecture: Lecture

) {
    fun toResponse() = LectureScheduleRequestDto(
        id = this.id,
        weekDay = this.weekDay,
        startAt = this.startAt,
        endAt = this.endAt,
    )
}