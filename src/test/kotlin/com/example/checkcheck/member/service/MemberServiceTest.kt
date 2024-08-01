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
import com.example.checkcheck.member.service.MemberService
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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
    val testConfirmPassword = "testtest1@"
    val testName = "test"

    val signUpDto = SignUpDto(
        testEmail, testPassword, testConfirmPassword, testName
    )

    val member = Member(
        id = null,
        email = testEmail,
        password = testPassword,
        confirmpassword = testConfirmPassword,
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

        val result = memberService.signup(signUpDto)

        verify(exactly = 1) { memberRepository.save(any()) }
        verify(exactly = 1) { memberRoleRepository.save(any()) }

        assertEquals("회원가입이 완료되었습니다!", result)

    }

    @Test
    fun `이메일은 중복될 수 없다`() {
        every { memberRepository.existsByEmail(testEmail) } returns true

        val result = memberRepository.existsByEmail(signUpDto.email)

        assertThrows<RuntimeException> { memberService.signup(signUpDto) }
        verify(exactly = 1) { memberRepository.existsByEmail(signUpDto.email) }
    }


    @Test
    fun `회원가입에 성공한 요청은 토큰을 반환한다`() {
        val loginSuccessToken = TokenInfo(
            grantType = "Bearer",
            accessToken = "testToken"
        )
        val loginDto = LoginDto(
            _email = testEmail,
            _password = testPassword,
        )
        every { authenticationManagerBuilder.`object`.authenticate(any()) } returns UsernamePasswordAuthenticationToken(testEmail, testPassword)
        every { jwtTokenProvider.createToken(authenticationManagerBuilder.`object`.authenticate(any())) } returns loginSuccessToken
        //every { jwtTokenProvider.createToken(any()) } returns loginSuccessToken

        val result = memberService.login(loginDto)

        verify(exactly = 1) { authenticationManagerBuilder.`object`.authenticate(any()) }
        verify(exactly = 1) { jwtTokenProvider.createToken(authenticationManagerBuilder.`object`.authenticate(any())) }
        //verify(exactly = 1) { jwtTokenProvider.createToken(any()) }

        assertEquals(result.grantType, "Bearer")
        assertEquals(result.accessToken, "testToken")
    }
}