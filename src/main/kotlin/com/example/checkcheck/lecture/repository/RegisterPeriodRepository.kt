package com.example.checkcheck.lecture.repository

import com.example.checkcheck.lecture.entity.RegisterPeriod
import org.springframework.data.jpa.repository.JpaRepository

interface RegisterPeriodRepository : JpaRepository<RegisterPeriod, Long>