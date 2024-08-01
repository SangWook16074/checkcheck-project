package com.example.checkcheck.memo.repository

import com.example.checkcheck.memo.entity.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemoRepository : JpaRepository<Memo, Long> {

    @Query("SELECT m FROM Memo m JOIN FETCH m.member JOIN FETCH m.lecture")
    fun findAllByFetchJoin(): List<Memo>

}



