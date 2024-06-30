package com.example.checkcheck.member.controller

import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/member")
class MemberController(
    private val memberService: MemberService
) {
    /**
     * 회원가입 Api
     */
    @PostMapping("/signup")
    private fun signup(signUpDto: SignUpDto) : ResponseEntity<BaseResponse<String>> {
        val result = memberService.signup(signUpDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(
            data = result
        ))
    }
}