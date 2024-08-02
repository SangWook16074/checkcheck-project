package com.example.checkcheck.memo.service

import com.example.checkcheck.common.exceptions.memo.MemoException
import com.example.checkcheck.lecture.repository.LectureRepository
import com.example.checkcheck.member.repository.MemberRepository
import com.example.checkcheck.memo.dto.MemoRequestDto
import com.example.checkcheck.memo.dto.MemoResponseDto
import com.example.checkcheck.memo.entity.Memo
import com.example.checkcheck.memo.repository.MemoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemoService (
    private val memoRepository: MemoRepository,
    private val lectureRepository: LectureRepository,
    private val memberRepository: MemberRepository
) {

    /**
     * 모든 메모를 조회
     */
    fun getMemos(): List<MemoResponseDto> {
        val result = memoRepository.findAllByFetchJoin()
        return result.map { it.toResponse() }
    }

    /**
     * 새로운 메모 생성
     */
    fun postMemos(memoRequestDto: MemoRequestDto): MemoResponseDto {
        val member = memberRepository.findByIdOrNull(memoRequestDto.memberId)
            ?: throw MemoException(msg = "존재하지 않는 멤버 ID입니다.")
        val lecture = lectureRepository.findByIdOrNull(memoRequestDto.lectureId)
            ?: throw MemoException(msg = "존재하지 않는 강의 ID입니다.")

        val memo = Memo(
            content = memoRequestDto.content,
            member = member,
            lecture = lecture
        )

        val savedMemo = memoRepository.save(memo)
        return savedMemo.toResponse()
    }

    /**
     * 메모 수정
     */
    fun putMemos(memoRequestDto: MemoRequestDto, id: Long): MemoResponseDto {
        val memo: Memo = memoRepository.findByIdOrNull(id)
            ?: throw MemoException(msg = "존재하지 않는 메모 ID입니다.")
        memo.content = memoRequestDto.content
        memoRepository.save(memo)
        return memo.toResponse()
    }

    /**
     * 메모 삭제
     */
    fun deleteMemo(id: Long) {
        memoRepository.deleteById(id)
    }

}