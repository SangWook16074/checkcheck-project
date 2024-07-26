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
    val registerStartDate: String,

    @Column(nullable = false)
    val registerEndDate: String,

    @Column(nullable = false)
    val registerStartAt: String,

    @Column(nullable = false)
    val registerEndAt: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_register_period_lecture_id"))
    val lecture: Lecture
)