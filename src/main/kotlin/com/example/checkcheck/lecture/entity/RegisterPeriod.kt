package com.example.checkcheck.lecture.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class RegisterPeriod(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val startAt : LocalDateTime,

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    val endAt : LocalDateTime,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(foreignKey = ForeignKey(name = "fk_register_period_lecture_id"))
    val lecture: Lecture
)