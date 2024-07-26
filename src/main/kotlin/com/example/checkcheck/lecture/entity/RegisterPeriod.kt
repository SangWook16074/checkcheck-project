package com.example.checkcheck.lecture.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class RegisterPeriod(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false)
    val registerStartDate: LocalDate,

    @Column(nullable = false)
    val registerEndDate: LocalDate,

    @Column(nullable = false)
    val registerStartAt: LocalTime,

    @Column(nullable = false)
    val registerEndAt: LocalTime,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_register_period_lecture_id"))
    val lecture: Lecture
)