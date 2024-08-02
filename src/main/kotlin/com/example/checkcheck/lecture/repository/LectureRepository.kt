package com.example.checkcheck.lecture.repository

import com.example.checkcheck.lecture.entity.Lecture
import com.example.checkcheck.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findByTitle(title : String) : Lecture?

    @Query(value = """
                SELECT DISTINCT l 
                FROM Lecture l
                LEFT JOIN FETCH l.registerPeriod
                LEFT JOIN FETCH l.lectureSchedule
                    """)
    fun findAllByFetchJoin() : List<Lecture>

    fun findByMember(member: Member): List<Lecture>

    fun countByMember(member: Member): Long
}