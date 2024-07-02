package com.example.checkcheck.member.service

import com.example.checkcheck.common.authority.JwtTokenProvider
import com.example.checkcheck.common.authority.TokenInfo
import com.example.checkcheck.common.enums.Role
import com.example.checkcheck.member.dto.LoginDto
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.entity.MemberRole
import com.example.checkcheck.member.repository.MemberRepository
import com.example.checkcheck.member.repository.MemberRoleRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,

    ) {
    /**
     * 회원 가입
     */
    fun signup(signUpDto: SignUpDto) : String {
        var member : Member? = memberRepository.findByEmail(signUpDto.email)
        // 중복회원 검사
        if (member != null) {
            throw RuntimeException("이미 가입한 회원입니다!")
        }
        memberRepository.save(signUpDto.toMember())

        member = signUpDto.toMember()
        memberRepository.save(member)

        val memberRole = MemberRole(
            id = null,
            role = Role.MEMBER,
            member = member,
        )
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다!"
    }

    /**
     * 로그인 -> 토큰 발행
     */

    fun login(loginDto: LoginDto) : TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }


}