package com.example.checkcheck.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,

    @Column(nullable = false, updatable = false, length = 100)
    val email : String,

    @Column(nullable = false, length = 1000)
    val password : String,

    @Column(nullable = false, length = 100)
    val name : String,
)