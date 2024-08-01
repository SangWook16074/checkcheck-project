package com.example.checkcheck.memo.entity

import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.memo.dto.MemoResponseDto
import jakarta.persistence.*



@Entity
data class Memo(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null, // 메모의 고유 ID (nullable)

    @Column(nullable = false, length = 1000)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_memo_id"))
    val member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_lecture_memo_id"))
    val lecture: Lecture,

) {
    fun toResponse() : MemoResponseDto = MemoResponseDto(
        id = id,
        content = content,
    )
}