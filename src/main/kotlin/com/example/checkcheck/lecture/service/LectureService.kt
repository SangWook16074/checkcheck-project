package com.example.checkcheck.lecture.service

import com.example.checkcheck.common.exceptions.lecture.LectureException
import com.example.checkcheck.lecture.dto.LectureRequestDto
import com.example.checkcheck.lecture.dto.LectureResponseDto
import com.example.checkcheck.lecture.dto.LectureScheduleRequestDto
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.entity.LectureSchedule
import com.example.checkcheck.lecture.entity.RegisterPeriod
import com.example.checkcheck.lecture.repository.LectureRepository
import com.example.checkcheck.lecture.repository.LectureScheduleRepository
import com.example.checkcheck.lecture.repository.RegisterPeriodRepository
import com.example.checkcheck.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Transactional
@Service
class LectureService (
    private val memberRepository: MemberRepository,
    private val lectureRepository: LectureRepository,
    private val registerPeriodRepository: RegisterPeriodRepository,
    private val lectureScheduleRepository: LectureScheduleRepository,
) {
    /**
     * 전체 강의 목록 조회
     */
    fun getLectures(): List<LectureResponseDto> {
        val result = lectureRepository.findAllByFetchJoin()
        return result.map { it.toResponse() }
    }

    /**
     * 강의 개설
     */
    fun postLectures(lectureRequestDto: LectureRequestDto, memberId: Long): String {
        var lecture = lectureRepository.findByTitle(lectureRequestDto.title)
        // 강의명 중복 검사
        if (lecture != null) {
            throw RuntimeException("이미 존재하는 강의이름입니다!")
        }
        // 사용자 유효성 검사
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw RuntimeException("존재하지 않는 사용자입니다!")

        lecture = Lecture(
            id = null,
            title = lectureRequestDto.title,
            member = member
        )

        // 강의 등록
        lectureRepository.save(lecture)

        val registerPeriod = RegisterPeriod(
            id = null,
            registerStartDate = LocalDate.parse(lectureRequestDto.registerStartDate),
            registerEndDate = LocalDate.parse(lectureRequestDto.registerEndDate),
            registerStartAt = LocalTime.parse(lectureRequestDto.registerStartAt),
            registerEndAt = LocalTime.parse(lectureRequestDto.registerEndAt),
            lecture = lecture
        )

        // 수강신청 기간 등록
        registerPeriodRepository.save(registerPeriod)

        val lectureSchedule = LectureSchedule(
            id = null,
            lectureStartDate = lectureRequestDto.lectureStartDate,
            lectureEndDate = lectureRequestDto.lectureEndDate,
            weekDay = lectureRequestDto.lectureWeekDay,
            lectureStartAt = lectureRequestDto.lectureStartAt,
            lectureEndAt = lectureRequestDto.lectureEndAt,
            lecturePlace = lectureRequestDto.lecturePlace,
            lectureInfo = lectureRequestDto.lectureInfo,
            lecture = lecture
        )

        // 강의 시간표 등록
        lectureScheduleRepository.save(lectureSchedule)
        return "강의가 등록되었습니다!"
    }

    /**
     * 사용자 id로 개별 강의 조회
     * isEmpty 사용 (result의 강의목록이 비어있는지 없는지 확인)
     */
    fun getLecturesByUserId(memberId: Long): List<LectureResponseDto> {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw RuntimeException("사용자가 존재하지 않습니다.")

        val result = lectureRepository.findByMember(member)
        if (result.isEmpty()) {
            throw LectureException("사용자가 등록한 강의가 존재하지 않습니다.")
        }
        return result.map { it.toResponse() }
    }

    /**
     * 강의시간표 요일 변경 Api
     */
    fun putLectureWeekDay(lectureScheduleRequestDto: LectureScheduleRequestDto, id: Long): LectureScheduleRequestDto {
        val lectureSchedule = lectureScheduleRepository.findByIdOrNull(id)
            ?: throw LectureException("존재하지 않는 강의 시간표 입니다.")

        lectureSchedule.weekDay = lectureScheduleRequestDto.weekDay

        val result = lectureScheduleRepository.save(lectureSchedule)
        return result.toResponse()
    }

    /**
     * 강의 시작시간 변경 Api
     */
    fun putLectureStartAt(lectureScheduleRequestDto: LectureScheduleRequestDto, id: Long): LectureScheduleRequestDto {
        val lectureSchedule = lectureScheduleRepository.findByIdOrNull(id)
            ?: throw LectureException("존재하지 않는 강의 시간표 입니다.")

        if (lectureScheduleRequestDto.lectureStartAtLocalTime.isBefore(LocalTime.of(9, 0))) {
            throw LectureException("강의 시작은 오전 9시 이후여야 합니다.")
        }

        lectureSchedule.lectureStartAt = lectureScheduleRequestDto.lectureStartAt

        val result = lectureScheduleRepository.save(lectureSchedule)
        return result.toResponse()
    }

    /**
     * 강의 종료시간 변경 Api
     */
    fun putLectureEndAt(lectureScheduleRequestDto: LectureScheduleRequestDto, id: Long): LectureScheduleRequestDto {
        val lectureSchedule = lectureScheduleRepository.findByIdOrNull(id)
            ?: throw LectureException("존재하지 않는 강의 시간표 입니다.")

        if (lectureScheduleRequestDto.lectureEndAtLocalTime.isAfter(LocalTime.of(18, 0))) {
            throw LectureException("강의 종료는 오후 6시 이전이어야 합니다.")
        }

        lectureSchedule.lectureEndAt = lectureScheduleRequestDto.lectureEndAt

        val result = lectureScheduleRepository.save(lectureSchedule)
        return result.toResponse()
    }

    /**
     * 강의 삭제
     */
    fun deleteLectures(id: Long) {
        lectureRepository.deleteById(id)
    }
}