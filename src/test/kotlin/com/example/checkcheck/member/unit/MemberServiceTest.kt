package com.example.checkcheck.member.unit
import com.example.checkcheck.common.authority.JwtTokenProvider
import com.example.checkcheck.common.enums.Role
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.entity.MemberRole
import com.example.checkcheck.member.repository.MemberRepository
import com.example.checkcheck.member.repository.MemberRoleRepository
import com.example.checkcheck.member.service.MemberService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import kotlin.test.assertEquals

class MemberServiceTest {
    private val memberRepository : MemberRepository = mockk()
    private val memberRoleRepository: MemberRoleRepository = mockk()
    private val jwtTokenProvider: JwtTokenProvider = mockk()
    private val authenticationManagerBuilder: AuthenticationManagerBuilder = mockk()
    private val memberService : MemberService = MemberService(memberRepository, memberRoleRepository, jwtTokenProvider, authenticationManagerBuilder)

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

    val memberRole = MemberRole(
        id = null,
        role = Role.MEMBER,
        member = member
    )


    @Test
    fun `회원가입에 성공할 경우 성공 메시지가 반환된다`() {
        every { memberRoleRepository.save(any()) } returns memberRole
        every { memberRepository.save(any()) } returns member
        every { memberRepository.findByEmail(testEmail) } returns null

        val result = memberService.signup(signUpDto)

        verify(exactly = 1) { memberRepository.save(any()) }
        verify(exactly = 1) { memberRepository.findByEmail(signUpDto.email) }
        verify(exactly = 1) { memberRoleRepository.save(any()) }

        assertEquals("회원가입이 완료되었습니다!", result)

    }

    @Test
    fun `이메일은 중복될 수 없다`() {

        every { memberRepository.findByEmail(testEmail) } returns member
        assertThrows<RuntimeException> { memberService.signup(signUpDto) }
        verify(exactly = 1) { memberRepository.findByEmail(signUpDto.email) }
    }
}