package com.example.checkcheck.member.dto

import com.example.checkcheck.member.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignUpDto(
    @field:NotBlank(message = "이메일을 입력해주세요!")
    @field:Email(message = "올바르지 못한 이메일 형식입니다!")
    @JsonProperty("email")
    private val _email : String?,

    @field:NotBlank(message = "비밀번호를 입력해주세요!")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-=_+,.<>/?'\"\\[\\]{}\\\\|])[a-zA-Z0-9!@#$%^&*()\\-=_+,.<>/?'\"\\[\\]{}\\\\|]{8,20}\$",
        message = "올바르지 못한 비밀번호 형식입니다!")
    @JsonProperty("password")
    private val _password : String?,

    @field:NotBlank(message = "이름을 입력해주세요!")
    @JsonProperty("name")
    private val _name : String?,
) {
    val email : String
        get() = _email!!

    val password : String
        get() = _password!!

    val name : String
        get() = _name!!

    fun toMember() : Member = Member(
        id = null,
        email = email,
        password = password,
        name = name,
    )
}
