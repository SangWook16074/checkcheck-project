package com.example.checkcheck.lecture.repository

import com.example.checkcheck.lecture.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository

interface LectureRepository : JpaRepository<Lecture, Long> {
    fun findByTitle(title : String) : Lecture

    fun findAllByFetchJoin() : List<Lecture>
}