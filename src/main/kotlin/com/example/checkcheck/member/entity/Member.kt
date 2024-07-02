package com.example.checkcheck.member.entity

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_email", columnNames = ["email"])]
)
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
) {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val role: List<MemberRole>? = null

}