package com.example.checkcheck.memo.repository

import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.member.entity.Member
import com.example.checkcheck.memo.entity.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemoRepository : JpaRepository<Memo, Long> {

    // 특정 사용자 ID의 특정 강의 ID를 기반으로 메모 조회
    fun findByMemberAndLecture(memberId: Long, lectureId: Long): List<Memo>
    // 연관된 객체와 함께 모든 메모 조회
    @Query("SELECT m FROM Memo m JOIN FETCH m.member JOIN FETCH m.lecture")
    fun findAllByFetchJoin(): List<Memo>


    // 특정 사용자 ID를 기반으로 모든 메모 조회
    @Query("SELECT m FROM Memo m WHERE m.member.id = :memberId")
    fun findByMemberId(@Param("memberId") memberId: Long): List<Memo>

}


