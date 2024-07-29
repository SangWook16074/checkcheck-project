package com.example.checkcheck.lecture.service

import com.example.checkcheck.common.enums.WeekDay
import com.example.checkcheck.lecture.dto.LectureRequestDto
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.LectureSchedule
import com.example.checkcheck.lecture.entity.RegisterPeriod
import com.example.checkcheck.lecture.repository.LectureRepository
import com.example.checkcheck.lecture.repository.LectureScheduleRepository
import com.example.checkcheck.lecture.repository.RegisterPeriodRepository
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.member.repository.MemberRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import kotlin.test.assertEquals

class LectureServiceTest {
    private val memberRepository : MemberRepository = mockk()
    private val lectureRepository : LectureRepository = mockk()
    private val registerPeriodRepository : RegisterPeriodRepository = mockk()
    private val lectureScheduleRepository : LectureScheduleRepository = mockk()
    private val lectureService : LectureService
    = LectureService(
        memberRepository = memberRepository,
        lectureRepository = lectureRepository,
        registerPeriodRepository = registerPeriodRepository,
        lectureScheduleRepository = lectureScheduleRepository,)

    @Test
    fun `강의 생성 테스트`() {
        val testEmail = "test@test.com"
        val testPassword = "testtest1@"
        val testName = "test"
        val member = Member(
            id = 1,
            email = testEmail,
            password = testPassword,
            name = testName
        )

        val testLectureRequestDto : LectureRequestDto = LectureRequestDto(
            _title = "test",
            _lectureStartDate = "23-07-01",
            _lectureEndDate = "23-07-30",
            _lectureStartAt = "09:00",
            _lectureEndAt = "10:00",
            _lectureWeekDay = "MON",
            _lecturePlace = "Test Room",
            _lectureInfo = "Test Lecture Information",
            _registerStartDate = "23-02-01",
            _registerEndDate = "24-02-25",
            _registerStartAt = "10:00",
            _registerEndAt = "11:00",
        )

        val testLecture = Lecture(
            id = 1,
            title = "test",
            member = member
        )
        val testLectureSchedule = LectureSchedule(
            id = null,
            lectureStartDate = "23-03-01",
            lectureEndDate = "23-06-30",
            lectureStartAt = "10:00",
            lectureEndAt = "13:00",
            weekDay = WeekDay.MON,
            lecturePlace = "Test Room",
            lectureInfo = "Test Lecture Information",
            lecture = testLecture
        )
        val testRegisterPeriod = RegisterPeriod(
            id = null,
            registerStartDate = "23-02-01",
            registerEndDate = "24-02-25",
            registerStartAt = "10:00",
            registerEndAt = "11:00",
            lecture = testLecture
        )
        every { memberRepository.findByIdOrNull(1) } returns member
        every { lectureRepository.save(any()) } returns testLecture
        every { lectureRepository.findByTitle(testLectureRequestDto.title) } returns null
        every { registerPeriodRepository.save(any()) } returns testRegisterPeriod
        every { lectureScheduleRepository.save(any()) } returns testLectureSchedule

        val result = lectureService.postLectures(testLectureRequestDto, 1)

        verify(exactly = 1) { lectureRepository.findByTitle(testLectureRequestDto.title) }
        verify(exactly = 1) { memberRepository.findByIdOrNull(1) }
        verify(exactly = 1) { lectureRepository.save(any()) }
        verify(exactly = 1) { lectureScheduleRepository.save(any()) }
        verify(exactly = 1) { registerPeriodRepository.save(any()) }

        assertEquals(result, "강의가 등록되었습니다!")
    }

    @Test
    fun `강의 조회 테스트`() {
        val testEmail = "test@test.com"
        val testPassword = "testtest1@"
        val testName = "test"
        val member = Member(
            id = 1,
            email = testEmail,
            password = testPassword,
            name = testName
        )
        val testLecture = Lecture(
            id = 1,
            title = "test",
            member = member
        )
        every { lectureRepository.findAllByFetchJoin() } returns listOf(testLecture)

        val result = lectureService.getLectures()

        verify(exactly = 1) { lectureRepository.findAllByFetchJoin() }
        assertEquals(result.size, 1)
    }
}