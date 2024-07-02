package com.example.checkcheck.member.controller

import com.example.checkcheck.common.authority.TokenInfo
import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.member.dto.LoginDto
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/member")
class MemberController(
    private val memberService: MemberService
) {
    /**
     * 회원가입 Api
     */
    @PostMapping("/signup")
    private fun signup(@Valid @RequestBody signUpDto: SignUpDto) : ResponseEntity<BaseResponse<String>> {
        val result = memberService.signup(signUpDto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse(data = result))
    }

    /**
     * 로그인 Api
     */
    @PostMapping("/login")
    private fun login(@Valid @RequestBody loginDto: LoginDto) : ResponseEntity<BaseResponse<TokenInfo>> {
        val tokenInfo = memberService.login(loginDto)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = tokenInfo))
    }




}