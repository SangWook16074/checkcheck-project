package com.example.checkcheck.lecture.repository

import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.RegisterPeriod
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegisterPeriodRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val lectureRepository: LectureRepository,
    private val registerPeriodRepository: RegisterPeriodRepository
) {



    @Test
    fun `수강신청기간 등록 테스트`() {
        val member = Member(
            id = null,
            email = "test@test.com",
            password = "testtest1@",
            name = "test"
        )

        val lecture = Lecture(
            id = null,
            title = "testLecture",
            member = member

        )
        memberRepository.save(member)
        lectureRepository.save(lecture)

        val registerPeriod = RegisterPeriod(
            id = null,
            registerStartDateTime = "23-02-01 10:00",
            registerEndDateTime = "24-02-25 10:00",
            lecture = lecture
        )

        val result = registerPeriodRepository.save(registerPeriod)

        assertThat(result.lecture.title).isEqualTo("testLecture")
        assertThat(result.lecture.member.name).isEqualTo("test")
        assertThat(result.lecture.member.email).isEqualTo("test@test.com")
        assertThat(result.lecture.member.password).isEqualTo("testtest1@")
    }
}