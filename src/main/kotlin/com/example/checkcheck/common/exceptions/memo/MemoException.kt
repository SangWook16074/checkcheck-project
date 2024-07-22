package com.example.checkcheck.common.exceptions.memo

class MemoException (
    val msg : String = "에러가 발생했습니다!"
) : RuntimeException(msg)