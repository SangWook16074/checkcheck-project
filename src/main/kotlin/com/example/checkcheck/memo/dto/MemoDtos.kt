package com.example.checkcheck.memo.dto

import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.memo.entity.Memo


data class MemoRequestDto(
    var id: Long?,
    var content: String,
    var member: Member?,
    var lecture: Lecture?,
) {
    fun toEntity(): Memo = Memo(
        content = content,
        member = member!!,
        lecture = lecture!!,
    )
}

data class MemoResponseDto(
    var id: Long?,
    var content: String,
)
