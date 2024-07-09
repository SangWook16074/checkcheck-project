package com.example.checkcheck.lecture.entity

import com.example.checkcheck.lecture.dto.LectureResponseDto
<<<<<<< HEAD
import jakarta.persistence.*
import java.time.LocalDateTime
=======
import com.example.checkcheck.member.entity.Member
import jakarta.persistence.*
>>>>>>> feature/lecture

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

    @Column(nullable = false, length = 100)
    var maxStudent : Int,

<<<<<<< HEAD
    //강의시간
    @Column(nullable = false, updatable = false, length = 50)
    var time: String,

    //수강신청기간
    @Column(nullable = false, updatable = false, length = 50)
    var period: String,

    //강의정보
    @Column(nullable = false, updatable = false, length = 200)
    var information: String

=======
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_id_member_id"))
    var member: Member
>>>>>>> feature/lecture
) {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.ALL])
    val resisterPeriod : ResisterPeriod? = null

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "lecture", cascade = [CascadeType.ALL])
    val lectureSchedule : LectureSchedule? = null


    fun toResponse() : LectureResponseDto = LectureResponseDto(
        title = title,
        maxStudent = maxStudent,
        resisterPeriod = resisterPeriod,
        lectureSchedule = lectureSchedule,
        member = member
    )
}