package com.example.checkcheck.lecture.controller

import com.example.checkcheck.common.dtos.CustomUser
import com.example.checkcheck.lecture.service.LectureService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.web.JsonPath
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(LectureController::class)
@AutoConfigureMockMvc(addFilters = false)
class LectureControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var lectureService: LectureService

    @BeforeEach
    fun setup() {
        // CustomUser 모킹
        val customUser = CustomUser(
            email = "test@test.com",
            password = "testtest1@",
            authority = listOf( SimpleGrantedAuthority("ROLE_MEMBER") ),
            id = 1L
        )

        // Authentication 모킹
        val authentication = mockk<Authentication>()
        every { authentication.principal } returns customUser

        // SecurityContext 모킹
        val securityContext = mockk<SecurityContext>()
        every { securityContext.authentication } returns authentication

        // SecurityContextHolder에 SecurityContext 설정
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `사용자 강의생성 응답코드 테스트 = 201`() {

        every { lectureService.postLectures(any(), 1L) } returns "강의가 등록되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/lecture/open")
                .content(
                    """
            {
                "title": "testLecture",
                "lectureStartDate": "21-07-03",
                "lectureEndDate": "21-07-30",
                "lectureStartAt": "09:00",
                "lectureEndAt": "10:00",
                "lectureWeekDay": "MON",
                "lecturePlace": "Test Room",
                "registerStartDate": "21-07-01",
                "registerEndDate": "21-07-30",
                "registerStartAt": "09:00",
                "registerEndAt": "10:00",
                "lectureInfo": "Test Lecture Information"
            }
            """.trimIndent()
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(
                status().isCreated
            )

        verify(exactly = 1) { lectureService.postLectures(any(), 1L) }
    }

    @Test
    fun `사용자 강의생성 응답 데이터 테스트`() {
        every { lectureService.postLectures(any(), 1L) } returns "강의가 등록되었습니다!"

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/lecture/open")
                .content("""
            {
                "title": "testLecture",
                "lectureStartDate": "21-07-03",
                "lectureEndDate": "21-07-30",
                "lectureStartAt": "09:00",
                "lectureEndAt": "10:00",
                "lectureWeekDay": "MON",
                "lecturePlace": "Test Room",
                "registerStartDate": "21-07-01",
                "registerEndDate": "21-07-30",
                "registerStartAt": "09:00",
                "registerEndAt": "10:00",
                "lectureInfo": "Test Lecture Information"
            }
            """.trimIndent())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.status").value("SUCCESS"))
            .andExpect(jsonPath("$.data").value("강의가 등록되었습니다!"))

        verify(exactly = 1) { lectureService.postLectures(any(), 1L) }
    }

    @Test
    fun `사용자 강의생성 Validation 응답 테스트`() {
        val invalidJsonContent = """
    {
        "title": "testLecture",
        "lectureStartDate": "21-07-03",
        "lectureEndDate": "21-07-30",
        "lectureStartAt": "0900",
        "lectureEndAt": "10:00",
        "lectureWeekDay": "MN",
        "lecturePlace": "Test Room",
        "registerStartDate": "21-07-01",
        "registerEndDate": "21-07-30",
        "registerStartAt": "09:00",
        "registerEndAt": "10:00",
        "lectureInfo": "Test Lecture Information"
    }
    """.trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/lecture/open")
                .content(invalidJsonContent)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.status").value("ERROR"))

        verify(exactly = 0) { lectureService.postLectures(any(), 1L) }
    }

}