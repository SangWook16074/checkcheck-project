package com.example.checkcheck.member.repository

import com.example.checkcheck.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email : String) : Member?
}