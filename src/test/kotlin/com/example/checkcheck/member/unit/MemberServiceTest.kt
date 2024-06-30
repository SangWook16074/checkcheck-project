package com.example.checkcheck.member.unit

import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import com.example.checkcheck.member.service.MemberService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class MemberServiceTest {
    private val memberRepository : MemberRepository = mockk()
    private val memberService : MemberService = MemberService(memberRepository)

    val testEmail = "test@test.com"
    val testPassword = "testtest1@"
    val testName = "test"
    val signUpDto = SignUpDto(
        testEmail, testPassword, testName
    )

    val member = Member(
        id = null,
        email = testEmail,
        password = testPassword,
        name = testName
    )

    @Test
    fun `회원가입에 성공할 경우 성공 메시지가 반환된다`() {
        every { memberRepository.save(any()) } returns member
        every { memberRepository.findByEmail(testEmail) } returns null

        val result = memberService.signup(signUpDto)

        verify(exactly = 1) { memberRepository.save(any()) }
        verify(exactly = 1) { memberRepository.findByEmail(signUpDto.email) }

        assertEquals("회원가입이 완료되었습니다!", result)

    }

    @Test
    fun `이메일은 중복될 수 없다`() {

        every { memberRepository.findByEmail(testEmail) } returns member
        assertThrows<RuntimeException> { memberService.signup(signUpDto) }
        verify(exactly = 1) { memberRepository.findByEmail(signUpDto.email) }
    }
}