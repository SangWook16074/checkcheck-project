package com.example.checkcheck.memo.service

import com.example.checkcheck.common.exceptions.memo.MemoException
import com.example.checkcheck.memo.dto.MemoRequestDto
import com.example.checkcheck.memo.dto.MemoResponseDto
import com.example.checkcheck.memo.entity.Memo
import com.example.checkcheck.memo.repository.MemoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemoService (
    private val memoRepository: MemoRepository,
//    private val memberRepository: MemberRepository,
//    private val lectureRepository: LectureRepository
) {

    /**
     * 모든 메모를 조회
     */
    fun getMemos(): List<MemoResponseDto> {
        val result = memoRepository.findAllByFetchJoin()
        return result.map { it.toResponse() }
    }

    /**
     * 특정 사용자 ID의 모든 메모를 가져옴. -> 메모 목록 검색 기능
     */
    fun getMemosByMemberWithSearch(memberId: Long, query: String): List<MemoResponseDto> {
        val memos = memoRepository.findByMemberId(memberId).filter {
            it.content.contains(query, ignoreCase = true) // 메모 내용에서 검색어 포함 여부를 확인
        }
        return memos.map { it.toResponse() }
    }

    /**
     * 특정 사용자 ID의 특정 강의의 메모를 가져옴
     */
    fun getMemosByMemberAndLecture(memberId: Long, lectureId: Long): List<MemoResponseDto> {
        val memos = memoRepository.findByMemberAndLecture(memberId, lectureId)
        return memos.map { it.toResponse() }
    }


    /**
     * 새로운 메모 생성
     */
    fun postMemos(memoRequestDto: MemoRequestDto): MemoResponseDto {
        val memo = memoRepository.save(memoRequestDto.toEntity())
        return memo.toResponse()
    }

    /**
     * 메모 수정
     */
    fun putMemos(memoRequestDto: MemoRequestDto, id: Long): MemoResponseDto {
        val memo: Memo = memoRepository.findByIdOrNull(id)
            ?: throw MemoException(msg = "존재하지 않는 메모 ID입니다.")
        memo.content = memoRequestDto.content ?: memo.content
        memoRepository.save(memo)
        return memo.toResponse()
    }

    /**
     * 메모 삭제함.
     * 특정 사용자의 특정 메모를 삭제하는 걸로 수정이 필요함.
     */
    fun deleteMemo(id: Long) {
        memoRepository.deleteById(id)
    }
}