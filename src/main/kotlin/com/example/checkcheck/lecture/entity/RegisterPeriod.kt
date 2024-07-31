package com.example.checkcheck.lecture.entity

import com.example.checkcheck.lecture.dto.RegisterPeriodRequestDto
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class RegisterPeriod(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column
    var registerStartDateTime: String,

    @Column
    var registerEndDateTime: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_register_period_lecture_id"))
    val lecture: Lecture
) {
    fun toResponse() = RegisterPeriodRequestDto(
        id = this.id,
        registerStartDateTime = this.registerStartDateTime,
        registerEndDateTime = this.registerEndDateTime
    )
}