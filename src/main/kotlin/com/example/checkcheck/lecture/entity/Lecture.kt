package com.example.checkcheck.lecture.entity

import com.example.checkcheck.lecture.dto.LectureResponseDto
import jakarta.persistence.*
import com.example.checkcheck.member.entity.Member

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_lecture_title", columnNames = ["title"])]
)
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,

    @Column(nullable = false, updatable = false, length = 50)
    var title: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_id_member_id"))
    var member: Member
) {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.ALL])
    val registerPeriod : RegisterPeriod? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.ALL])
    val lectureSchedule : List<LectureSchedule>? = null


    fun toResponse() : LectureResponseDto = LectureResponseDto(
        title = title,
        registerPeriod = registerPeriod,
        lectureSchedule = lectureSchedule,
        member = member
    )
}