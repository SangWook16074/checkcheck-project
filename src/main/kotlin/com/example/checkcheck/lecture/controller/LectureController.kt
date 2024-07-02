package com.example.checkcheck.lecture.controller

import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.lecture.dto.LectureRequestDto
import com.example.checkcheck.lecture.dto.LectureResponseDto
import com.example.checkcheck.lecture.service.LectureService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Lecture-Controller", description = "강의 목록 조회, 개설, 삭제 관련 컨트롤러입니다")
@RestController
@RequestMapping("api/lecture")

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
            ResponseEntity<BaseResponse<LectureResponseDto>> {
        val result = lectureService.postLectures(lectureRequestDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }
}