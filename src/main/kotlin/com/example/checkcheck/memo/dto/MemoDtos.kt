package com.example.checkcheck.memo.dto

import jakarta.validation.constraints.NotNull


data class MemoRequestDto(
    var id: Long?,

    var content: String,

    @field:NotNull(message = "멤버 ID는 필수입니다.")
    var memberId: Long?,

    @field:NotNull(message = "강의 ID는 필수입니다.")
    var lectureId: Long?,
)

data class MemoResponseDto(
    var id: Long?,
    var content: String,
)
