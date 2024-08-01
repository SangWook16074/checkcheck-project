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
import jakarta.transaction.Transactional
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
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

        // 비밀번호 확인
        checkPassword(signUpDto.password, signUpDto.confirmPassword)

        // 회원정보 저장
        val member = memberRepository.save(signUpDto.toMember())

        val memberRole = MemberRole(
            id = null,
            role = Role.MEMBER,
            member = member,
        )
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다!"
    }

    /**
     * 비밀번호 확인
     */
    fun checkPassword(password: String, confirmPassword: String): String{
        if(password != confirmPassword) {
            throw RuntimeException("비밀번호가 일치하지 않습니다.")
        }
        return "비밀번호가 일치합니다."
    }

    /**
     * 이메일 중복 확인
     */
    fun checkEmailExists(email: String): String {
        val exists = memberRepository.existsByEmail(email)
        if(exists) {
            throw RuntimeException("이미 가입된 이메일입니다.")
        }
        return "이메일 확인이 완료되었습니다."
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