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
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.assertEquals

class LectureServiceTest {
    private val memberRepository : MemberRepository = mockk()
    private val lectureRepository : LectureRepository = mockk()
    private val registerPeriodRepository : RegisterPeriodRepository = mockk()
    private val lectureScheduleRepository : LectureScheduleRepository = mockk()
    private val lectureService : LectureService = LectureService(
        memberRepository = memberRepository,
        lectureRepository = lectureRepository,
        registerPeriodRepository = registerPeriodRepository,
        lectureScheduleRepository = lectureScheduleRepository,
    )

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

        val testLectureRequestDto = LectureRequestDto(
            _title = "test",
            _lectureStartDate = "23-07-01",
            _lectureEndDate = "23-07-30",
            _lectureStartAt = "09:00",
            _lectureEndAt = "10:00",
            _lectureWeekDay = WeekDay.MON,
            _lecturePlace = "Test Room",
            _lectureInfo = "Test Lecture Information",
            _registerStartDateTime = "23-02-01 10:00",
            _registerEndDateTime = "24-02-25 10:00",
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
            registerStartDateTime = "23-02-01 10:00",
            registerEndDateTime = "24-02-25 10:00",
            lecture = testLecture
        )

        every { memberRepository.findByIdOrNull(1) } returns member
        every { lectureRepository.save(any()) } returns testLecture
        every { lectureRepository.findByTitle(testLectureRequestDto.title) } returns null
        every { lectureRepository.countByMember(member) } returns 0
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
    fun `강의 개설 수 제한 테스트`() {
        val testEmail = "test@test.com"
        val testPassword = "testtest1@"
        val testName = "test"
        val member = Member(
            id = 1,
            email = testEmail,
            password = testPassword,
            name = testName
        )

        val testLectureRequestDto = LectureRequestDto(
            _title = "test",
            _lectureStartDate = "23-07-01",
            _lectureEndDate = "23-07-30",
            _lectureStartAt = "09:00",
            _lectureEndAt = "10:00",
            _lectureWeekDay = WeekDay.MON,
            _lecturePlace = "Test Room",
            _lectureInfo = "Test Lecture Information",
            _registerStartDateTime = "23-02-01 10:00",
            _registerEndDateTime = "24-02-25 10:00",
        )

        every { memberRepository.findByIdOrNull(1) } returns member
        every { lectureRepository.findByTitle(testLectureRequestDto.title) } returns null
        every { lectureRepository.countByMember(member) } returns 4

        val exception = assertThrows<RuntimeException> {
            lectureService.postLectures(testLectureRequestDto, 1)
        }

        assertEquals("한 사용자는 최대 4개의 강의만 생성할 수 있습니다.", exception.message)
    }

    @Test
    fun `강의명 중복 검사 테스트`() {
        val testEmail = "test@test.com"
        val testPassword = "testtest1@"
        val testName = "test"
        val member = Member(
            id = 1,
            email = testEmail,
            password = testPassword,
            name = testName
        )

        val testLectureRequestDto = LectureRequestDto(
            _title = "test",
            _lectureStartDate = "23-07-01",
            _lectureEndDate = "23-07-30",
            _lectureStartAt = "09:00",
            _lectureEndAt = "10:00",
            _lectureWeekDay = WeekDay.MON,
            _lecturePlace = "Test Room",
            _lectureInfo = "Test Lecture Information",
            _registerStartDateTime = "23-02-01 10:00",
            _registerEndDateTime = "24-02-25 10:00",
        )

        val existingLecture = Lecture(
            id = 1,
            title = "test",
            member = member
        )

        every { memberRepository.findByIdOrNull(1) } returns member
        every { lectureRepository.findByTitle(testLectureRequestDto.title) } returns existingLecture

        val exception = assertThrows<RuntimeException> {
            lectureService.postLectures(testLectureRequestDto, 1)
        }

        assertEquals("이미 존재하는 강의이름입니다!", exception.message)
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