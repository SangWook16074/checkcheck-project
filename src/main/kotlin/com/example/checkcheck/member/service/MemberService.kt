package com.example.checkcheck.member.service

import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    /**
     * 회원 가입
     */
    fun signup(signUpDto: SignUpDto) : String {
        val member = memberRepository.findByEmail(signUpDto.email)
        // 중복회원 검사
        if (member != null) {
            throw RuntimeException("이미 가입한 회원입니다!")
        }
        memberRepository.save(signUpDto.toMember())
        return "회원가입이 완료되었습니다!"
    }
}