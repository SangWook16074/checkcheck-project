package com.example.checkcheck.lecture.controller

import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.common.dtos.CustomUser
import com.example.checkcheck.lecture.dto.LectureRequestDto
import com.example.checkcheck.lecture.dto.LectureResponseDto
import com.example.checkcheck.lecture.dto.LectureScheduleRequestDto
import com.example.checkcheck.lecture.dto.RegisterPeriodRequestDto
import com.example.checkcheck.lecture.service.LectureService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Lecture-Controller", description = "강의 및 수강신청 조회, 개설, 수정, 삭제 관련 컨트롤러입니다")
@RestController
@RequestMapping("/api/lecture")

class LectureController(
    private val lectureService : LectureService
) {
    /**
     * 전체 강의 목록 조회 Api
     */
    @Operation(summary = "강의 목록", description = "강의 목록 조회 Api 입니다")
    @GetMapping("/list")
    private fun getLectures(): ResponseEntity<BaseResponse<List<LectureResponseDto>>> {
        val result = lectureService.getLectures()
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의 개설 APi
     */
    @Operation(summary = "강의 개설", description = "강의 개설 Api 입니다")
    @PostMapping("/open")
    private fun postLectures(@Valid @RequestBody lectureRequestDto: LectureRequestDto):
            ResponseEntity<BaseResponse<String>> {
        val memberId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).id
        val result = lectureService.postLectures(lectureRequestDto, memberId)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    /**
     * 사용자 id를 통해 강의 조회 Api
     */
    @Operation(summary = "사용자별 강의 조회", description = "사용자별 강의 조회 Api 입니다")
    @GetMapping("/userLecture/{memberId}")
    fun getLecturesByUserId(@PathVariable memberId: Long):
            ResponseEntity<BaseResponse<List<LectureResponseDto>>> {
        val result = lectureService.getLecturesByUserId(memberId)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의 id를 통해 강의 삭제 Api
     */
    @Operation(summary = "강의 삭제", description = "강의 삭제 Api 입니다")
    @DeleteMapping("/{id}")
    private fun deleteLectures(@PathVariable id: Long): ResponseEntity<BaseResponse<Any>> {
        lectureService.deleteLectures(id)
        return ResponseEntity.ok().body(BaseResponse(data = null))
    }

    /**
     * 강의 요일 변경 Api
     */
    @Operation(summary = "강의 요일 변경", description = "강의 요일 변경 Api 입니다")
    @PutMapping("/weekday/{id}")
    private fun putLectureWeekDay(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLectureWeekDay(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의 시작시간 변경 Api
     */
    @Operation(summary = "강의 시작시간 변경", description = "강의 시작시간 변경 Api 입니다")
    @PutMapping("/lectureStartAt/{id}")
    private fun putLectureStartAt(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLectureStartAt(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의 종료시간 변경 Api
     */
    @Operation(summary = "강의 종료시간 변경", description = "강의 종료시간 변경 Api 입니다")
    @PutMapping("/lectureEndAt/{id}")
    private fun putLectureEndAt(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLectureEndAt(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     *  강의 시작기간 변경 Api
     */
    @Operation(summary = "강의 시작기간 변경", description = "강의 시작기간 변경 Api 입니다")
    @PutMapping("/lectureStartDate/{id}")
    private fun putLectureStartDate(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLectureStartDate(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의 종료기간 변경 Api
     */
    @Operation(summary = "강의 종료기간 변경", description = "강의 종료기간 변경 Api 입니다")
    @PutMapping("/lectureEndDate/{id}")
    private fun putLectureEndDate(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLectureEndDate(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의실 변경 Api
     */
    @Operation(summary = "강의실 변경", description = "강의실 변경 Api 입니다")
    @PutMapping("lecturePlace/{id}")
    private fun putLecturePlace(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLecturePlace(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 강의 정보 변경 Api
     */
    @Operation(summary = "강의 정보 변경", description = "강의 정보 변경 Api 입니다")
    @PutMapping("/lectureInfo/{id}")
    private fun putLectureInfo(@Valid @RequestBody lectureScheduleRequestDto: LectureScheduleRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<LectureScheduleRequestDto>> {
        val result = lectureService.putLectureInfo(lectureScheduleRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 수강신청 시작 변경 Api
     */
    @Operation(summary = "수강신청 시작 일정 변경", description = "수강신청 시작 변경 Api 입니다")
    @PutMapping("/periodStartDateTime/{id}")
    private fun putRegisterStartDateTime(@Valid @RequestBody registerPeriodRequestDto: RegisterPeriodRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<RegisterPeriodRequestDto>> {
        val result = lectureService.putRegisterStartDateTime(registerPeriodRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 수강신청 종료 변경 Api
     */
    @Operation(summary = "수강신청 종료 일정 변경", description = "수강신청 시작 변경 Api 입니다")
    @PutMapping("/periodEndDateTime/{id}")
    private fun putRegisterEndDateTime(@Valid @RequestBody registerPeriodRequestDto: RegisterPeriodRequestDto, @PathVariable id: Long):
            ResponseEntity<BaseResponse<RegisterPeriodRequestDto>> {
        val result = lectureService.putRegisterEndDateTime(registerPeriodRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }
}