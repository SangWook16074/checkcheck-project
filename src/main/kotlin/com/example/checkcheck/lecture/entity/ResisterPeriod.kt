package com.example.checkcheck.lecture.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
class ResisterPeriod(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val startAt : LocalDateTime,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val endAt : LocalDateTime,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_resister_period_lecture_id"))
    val lecture: Lecture
)