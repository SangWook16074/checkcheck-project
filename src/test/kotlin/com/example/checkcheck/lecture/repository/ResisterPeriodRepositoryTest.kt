package com.example.checkcheck.lecture.repository

import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.ResisterPeriod
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
class ResisterPeriodRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val lectureRepository: LectureRepository,
    private val resisterPeriodRepository: ResisterPeriodRepository
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
            maxStudent = 40,
            member = member

        )
        memberRepository.save(member)
        lectureRepository.save(lecture)

        val resisterPeriod = ResisterPeriod(
            id = 1,
            startAt = LocalDateTime.now(),
            endAt = LocalDateTime.now(),
            lecture = lecture
        )

        val result = resisterPeriodRepository.save(resisterPeriod)

        assertThat(result.lecture.title).isSameAs("testLecture")
        assertThat(result.id).isSameAs(1L)
        assertThat(result.lecture.member.name).isSameAs("test")
        assertThat(result.lecture.member.email).isSameAs("test@test.com")
        assertThat(result.lecture.member.password).isSameAs("testtest1@")
    }
}