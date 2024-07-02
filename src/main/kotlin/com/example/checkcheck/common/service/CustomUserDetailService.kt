package com.example.checkcheck.common.service

import com.example.checkcheck.common.dtos.CustomUser
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return memberRepository.findByEmail(username)
            ?.let { createUserDetails(it) }
            ?: throw UsernameNotFoundException("해당하는 유저는 존재하지 않습니다.")
    }

    private fun createUserDetails(member : Member) : UserDetails {
        return CustomUser(
            member.id!!,
            member.email,
            passwordEncoder.encode(member.password),
            member.role!!.map { SimpleGrantedAuthority("ROLE_${it.role}") }
        )
    }
}