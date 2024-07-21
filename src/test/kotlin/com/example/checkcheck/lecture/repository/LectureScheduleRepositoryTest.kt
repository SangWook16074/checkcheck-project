package com.example.checkcheck.lecture.repository

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.LectureSchedule
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalTime

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class LectureScheduleRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val lectureRepository: LectureRepository,
    private val lectureScheduleRepository: LectureScheduleRepository,
) {

    @Test
    fun `강의 스케줄 등록 테스트`() {
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

        val lectureSchedule = LectureSchedule(
            id = 1,
            startAt = "09:00",
            endAt = "10:00",
            weekDay = WeekDay.MON,
            lecture = lecture
        )

        val result = lectureScheduleRepository.save(lectureSchedule)

        assertThat(result.weekDay).isEqualTo(WeekDay.MON)
        assertThat(result.lecture.title).isEqualTo("testLecture")
        assertThat(result.lecture.maxStudent).isEqualTo(40)
        assertThat(result.lecture.member.name).isEqualTo("test")
        assertThat(result.lecture.member.email).isEqualTo("test@test.com")
        assertThat(result.lecture.member.password).isEqualTo("testtest1@")
    }
}