package com.example.checkcheck.lecture.service

import com.example.checkcheck.lecture.dto.LectureRequestDto
import com.example.checkcheck.lecture.dto.LectureResponseDto
import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.lecture.repository.LectureRepository
import org.springframework.stereotype.Service

@Service
class LectureService (
    private val lectureRepository: LectureRepository
) {
    /**
     * 전체 강의 목록 조회
     */
    fun getLectures() : List<LectureResponseDto> {
        val result = lectureRepository.findAllByFetchJoin()
        return result.map {it.toResponse() }
    }

    /**
     * 강의 개설
     */
    fun postLectures(lectureRequestDto: LectureRequestDto) : LectureResponseDto {
        val result = lectureRepository.save(lectureRequestDto.toEntity())
        return result.toResponse()
    }
}