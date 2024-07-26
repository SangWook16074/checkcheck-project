package com.example.checkcheck.lecture.entity

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.dto.LectureScheduleRequestDto
import jakarta.persistence.*

@Entity
class LectureSchedule (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false)
    var lectureStartDate: String,

    @Column(nullable = false)
    var lectureEndDate: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var weekDay: WeekDay,

    @Column(nullable = false)
    var lectureStartAt: String,

    @Column(nullable = false)
    var lectureEndAt: String,

    @Column(nullable = false)
    var lecturePlace: String,

    @Column(length = 300)
    var lectureInfo: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_schedule_lecture_id"))
    var lecture: Lecture
) {
    fun toResponse() = LectureScheduleRequestDto(
        id = this.id,
        lectureStartDate = this.lectureStartDate,
        lectureEndDate = this.lectureEndDate,
        weekDay = this.weekDay,
        lectureStartAt = this.lectureStartAt,
        lectureEndAt = this.lectureEndAt,
        lecturePlace = this.lecturePlace,
        lectureInfo = this.lectureInfo
    )
}