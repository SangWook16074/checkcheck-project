package com.example.checkcheck.lecture.entity

import com.example.checkcheck.lecture.dto.LectureResponseDto
import com.example.checkcheck.lecture.dto.RegisterPeriodDto
import com.example.checkcheck.lecture.dto.LectureScheduleDto
import com.example.checkcheck.lecture.dto.MemberDto
import jakarta.persistence.*
import com.example.checkcheck.member.entity.Member

@Entity
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false, updatable = false, length = 50)
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_member_id"))
    var member: Member
) {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.ALL])
    var registerPeriod: RegisterPeriod? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.ALL])
    var lectureSchedule: List<LectureSchedule>? = null

    fun toResponse(): LectureResponseDto = LectureResponseDto(
        title = title,
        registerPeriod = registerPeriod?.toDto(),
        lectureSchedule = lectureSchedule?.map { it.toDto() },
        member = member.toDto()
    )
}

fun RegisterPeriod.toDto() = RegisterPeriodDto(
    registerStartDateTime = this.registerStartDateTime,
    registerEndDateTime = this.registerEndDateTime,
)

fun LectureSchedule.toDto() = LectureScheduleDto(
    id = this.id!!,
    weekday = this.weekDay.name,
    lectureStartAt = this.lectureStartAt,
    lectureEndAt = this.lectureEndAt,
    lectureStartDate = this.lectureStartDate,
    lectureEndDate = this.lectureEndDate,
    lecturePlace = this.lecturePlace,
    lectureInfo = this.lectureInfo
)

fun Member.toDto() = MemberDto(
    id = this.id!!,
    name = this.name
)
