package com.example.checkcheck.member.controller

import com.example.checkcheck.common.authority.TokenInfo
import com.example.checkcheck.common.config.SecurityConfig
import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.common.enums.ResultStatus
import com.example.checkcheck.member.dto.LoginDto
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.service.MemberService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import jdk.jfr.ContentType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(MemberController::class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest(

) {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var memberService: MemberService

    val testEmail = "test@test.com"
    val testPassword = "testtest1@"
    val testName = "test"
    val signUpDto = SignUpDto(
        testEmail, testPassword, testName
    )
    val loginDto = LoginDto(
        testEmail, testPassword
    )


    val loginSuccessToken = TokenInfo(
        grantType = "Bearer",
        accessToken = "testToken"
    )

    @Test
    fun `회원가입시 클라이언트는 201의 응답코드를 전달받는다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"email\":\"test@test.com\", \"password\":\"testtest1@\", \"name\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isCreated
        )
    }

    @Test
    fun `회원가입이 성공적으로 이루어지면 클라이언트는 "회원가입이 완료되었습니다!"라는 데이터를 전달받는다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"email\":\"test@test.com\", \"password\":\"testtest1@\", \"name\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.data").value("회원가입이 완료되었습니다!")
        )
    }

    @Test
    fun `회원가입시 이메일 형식을 지키지 않는 DTO는 에러가 반환된다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"email\":\"testtest.com\", \"password\":\"testtest1@\", \"name\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isBadRequest
        ).andExpect(
            content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.status").value("ERROR")
        )
            .andExpect(
                jsonPath("$.data._email").value("올바르지 못한 이메일 형식입니다!")
            )
            .andExpect(
                jsonPath("$.resultMsg").value("에러가 발생했습니다!")
            )
    }

    @Test
    fun `회원가입시 비밀번호가 8자이상 20자 이하 영소대소문자특수문자를 포함하는 형식을 지키지 않는 DTO는 에러가 반환된다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"email\":\"testtest.com\", \"password\":\"testtes\", \"name\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isBadRequest
        ).andExpect(
            content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.status").value("ERROR")
        )
            .andExpect(
                jsonPath("$.data._password").value("올바르지 못한 비밀번호 형식입니다!")
            )
            .andExpect(
                jsonPath("$.resultMsg").value("에러가 발생했습니다!")
            )
    }

    @Test
    fun `회원가입시 이메일을 입력하지 않는 DTO는 에러가 반환된다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"password\":\"testtest1@\", \"name\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isBadRequest
        ).andExpect(
            content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.status").value("ERROR")
        )
            .andExpect(
                jsonPath("$.data._email").value("이메일을 입력해주세요!")
            )
            .andExpect(
                jsonPath("$.resultMsg").value("에러가 발생했습니다!")
            )
    }

    @Test
    fun `회원가입시 비밀번호를 입력하지 않는 DTO는 에러가 반환된다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"email\":\"testtest.com\", \"name\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isBadRequest
        ).andExpect(
            content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.status").value("ERROR")
        )
            .andExpect(
                jsonPath("$.data._password").value("비밀번호를 입력해주세요!")
            )
            .andExpect(
                jsonPath("$.resultMsg").value("에러가 발생했습니다!")
            )
    }

    @Test
    fun `회원가입시 이름을 입력하지 않는 DTO는 에러가 반환된다`() {
        every { memberService.signup(signUpDto) } returns "회원가입이 완료되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/signup")
                .content("{\"email\":\"testtest.com\", \"password\":\"testtest1@\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isBadRequest
        ).andExpect(
            content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.status").value("ERROR")
        )
        .andExpect(
            jsonPath("$.data._name").value("이름을 입력해주세요!")
        )
        .andExpect(
            jsonPath("$.resultMsg").value("에러가 발생했습니다!")
        )
    }

    @Test
    fun `회원가입에 성공한 사용자는 200의 응답코드를 전달받는다`() {
        every { memberService.login(loginDto) } returns loginSuccessToken

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/login")
                .content("{\"email\":\"test@test.com\", \"password\":\"testtest1@\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `회원가입에 성공한 사용자는 토큰 정보를 전달 받는다`() {
        every { memberService.login(loginDto) } returns loginSuccessToken

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/member/login")
                .content("{\"email\":\"test@test.com\", \"password\":\"testtest1@\"}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            jsonPath("$.status").value("SUCCESS")
        )
        .andExpect(
            jsonPath("$.data.grantType").value("Bearer")
        )
        .andExpect(
            jsonPath("$.data.accessToken").value("testToken")
        )
        .andExpect(
            jsonPath("$.resultMsg").value("요청이 성공했습니다!")
        )
    }
}