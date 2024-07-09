package com.example.checkcheck.lecture.repository

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.LectureSchedule
import com.example.checkcheck.lecture.entity.ResisterPeriod
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime
import java.time.LocalTime

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class LectureRepositoryTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val lectureRepository: LectureRepository,
    private val lectureScheduleRepository: LectureScheduleRepository,
    private val resisterPeriodRepository: ResisterPeriodRepository,
) {

    @Test
    fun `강의 등록 테스트`() {
        val member = Member(
            id = 1,
            email = "test@test.com",
            password = "testtest1@",
            name = "test"
        )
        memberRepository.save(member)

        val lecture = Lecture(
            id = 1,
            title = "testLecture",
            maxStudent = 40,
            member = member

        )

        val result = lectureRepository.save(lecture)


        assertThat(result.id).isSameAs(1L)
        assertThat(result.title).isSameAs("testLecture")
        assertThat(result.maxStudent).isSameAs(40)
        assertThat(result.member.id).isSameAs(1L)
        assertThat(result.member.name).isSameAs("test")
        assertThat(result.member.email).isSameAs("test@test.com")
        assertThat(result.member.password).isSameAs("testtest1@")
    }

    @Test
    fun `강의 조회 테스트`() {
        val member = Member(
            id = 1,
            email = "test@test.com",
            password = "testtest1@",
            name = "test"
        )

        val lecture = Lecture(
            id = 1,
            title = "testLecture",
            maxStudent = 40,
            member = member

        )

        val lectureSchedule = LectureSchedule(
            id = null,
            startAt = LocalTime.now(),
            endAt = LocalTime.now(),
            weekDay = WeekDay.MON,
            lecture = lecture
        )

        val resisterPeriod = ResisterPeriod(
            id = null,
            startAt = LocalDateTime.now(),
            endAt = LocalDateTime.now(),
            lecture = lecture
        )

        memberRepository.save(member)
        lectureRepository.save(lecture)
        lectureScheduleRepository.save(lectureSchedule)
        resisterPeriodRepository.save(resisterPeriod)

        val result = lectureRepository.findAllByFetchJoin()

        assertThat(result.size).isSameAs(1)
        assertThat(result[0].id).isSameAs(1L)
        assertThat(result[0].title).isSameAs("testLecture")
        assertThat(result[0].member.id).isSameAs(1L)
        assertThat(result[0].lectureSchedule!!.id).isSameAs(1L)
        assertThat(result[0].resisterPeriod!!.id).isSameAs(1L)

    }
}