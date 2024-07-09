package com.example.checkcheck.common.exceptions.lecture

class LectureException(
    val msg: String = "에러가 발생했습니다."
) : RuntimeException(msg)