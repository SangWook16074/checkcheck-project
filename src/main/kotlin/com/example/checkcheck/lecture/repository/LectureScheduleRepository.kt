package com.example.checkcheck.lecture.repository

import com.example.checkcheck.lecture.entity.LectureSchedule
import org.springframework.data.jpa.repository.JpaRepository

interface LectureScheduleRepository : JpaRepository<LectureSchedule, Long>