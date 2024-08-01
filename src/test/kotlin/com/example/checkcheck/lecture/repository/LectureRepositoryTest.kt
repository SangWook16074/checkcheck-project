package com.example.checkcheck.lecture.repository

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.LectureSchedule
import com.example.checkcheck.lecture.entity.RegisterPeriod
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalDate
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
    private val registerPeriodRepository: RegisterPeriodRepository,
) {
    val member = Member(
        id = null,
        email = "test@test.com",
        password = "testtest1@",
        confirmpassword = "testtest1@",
        name = "test"
    )

    @BeforeEach
    fun setup() {
        memberRepository.save(member)
    }


    @Test
    fun `강의 등록 테스트`() {
        val lecture = Lecture(
            id = null,
            title = "testLecture",
            member = member
        )
        val result = lectureRepository.save(lecture)


        assertThat(result.title).isEqualTo("testLecture")
        assertThat(result.member.name).isEqualTo("test")
        assertThat(result.member.email).isEqualTo("test@test.com")
        assertThat(result.member.password).isEqualTo("testtest1@")
    }

    @Test
    fun `강의 조회 테스트`() {
        val lecture = Lecture(
            id = null,
            title = "testLecture",
            member = member
        )
        val l = lectureRepository.save(lecture)
        val lectureSchedule = LectureSchedule(
            id = null,
            lectureStartDate = "23-07-01",
            lectureEndDate = "23-07-30",
            lectureStartAt = "09:00",
            lectureEndAt = "10:00",
            weekDay = WeekDay.MON,
            lecturePlace = "Test Room",
            lectureInfo = "Test Lecture Information",
            lecture = l
        )

        val registerPeriod = RegisterPeriod(
            id = null,
            registerStartDateTime = "23-02-01 10:00",
            registerEndDateTime = "24-02-25 10:00",
            lecture = l
        )

        lectureScheduleRepository.save(lectureSchedule)
        registerPeriodRepository.save(registerPeriod)

        val result = lectureRepository.findAllByFetchJoin()

        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().title).isEqualTo("testLecture")
    }
}