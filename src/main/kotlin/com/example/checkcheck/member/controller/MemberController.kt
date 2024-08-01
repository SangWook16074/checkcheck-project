package com.example.checkcheck.member.controller

import com.example.checkcheck.common.authority.TokenInfo
import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.member.dto.LoginDto
import com.example.checkcheck.member.dto.SignUpDto
import com.example.checkcheck.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    fun signup(@Valid @RequestBody signUpDto: SignUpDto) : ResponseEntity<BaseResponse<String>> {
        val result = memberService.signup(signUpDto)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse(data = result))
    }

    /**
     * 로그인 Api
     */
    @Operation(summary = "로그인", description = "회원의 로그인 Api입니다.")
    @PostMapping("/login")
    fun login(@Valid @RequestBody loginDto: LoginDto) : ResponseEntity<BaseResponse<TokenInfo>> {
        val tokenInfo = memberService.login(loginDto)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = tokenInfo))
    }

    /**
     * 이메일 중복 확인 API
     */
    @Operation(summary = "이메일 중복 확인", description = "회원가입 시 이메일 중복을 확인하는 Api입니다.")
    @GetMapping("/check-email")
    fun checkEmail(@RequestParam email: String): ResponseEntity<BaseResponse<String>> {
        val emailExists = memberService.checkEmailExists(email)
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse(data = emailExists))
    }
}