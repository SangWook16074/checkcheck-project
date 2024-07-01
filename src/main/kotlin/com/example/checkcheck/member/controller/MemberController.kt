package com.example.checkcheck.member.controller

import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Member-Controller", description = "회원의 인증인가 관련 컨트롤러입니다.")
@RestController
@RequestMapping("api/member")
class MemberController(
    private val memberService: MemberService
) {
    /**
     * 회원가입 Api
     */
    @Operation(summary = "회원가입", description = "회원의 회원가입 Api입니다.")
    @PostMapping("/signup")
    private fun signup(@Valid @RequestBody signUpDto: SignUpDto) : ResponseEntity<BaseResponse<String>> {
        val result = memberService.signup(signUpDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(
            data = result
        ))
    }
}