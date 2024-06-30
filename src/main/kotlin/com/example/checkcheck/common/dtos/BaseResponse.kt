package com.example.checkcheck.common.dtos

import com.example.checkcheck.common.enums.ResultStatus

data class BaseResponse<T>(
    val status : String = ResultStatus.SUCCESS.name,
    val data : T?,
    val resultMsg : String = ResultStatus.SUCCESS.msg,
)
