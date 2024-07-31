package com.example.checkcheck.lecture.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
class RegisterPeriod(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column
    val registerStartDateTime: String,

    @Column
    val registerEndDateTime: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_register_period_lecture_id"))
    val lecture: Lecture
)