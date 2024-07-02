package com.example.checkcheck.member.entity

import com.example.checkcheck.common.enums.Role
import jakarta.persistence.*

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long?,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role : Role,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member : Member,

    )