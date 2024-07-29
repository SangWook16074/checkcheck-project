package com.example.checkcheck.memo.controller

import com.example.checkcheck.common.dtos.BaseResponse
import com.example.checkcheck.memo.dto.MemoRequestDto
import com.example.checkcheck.memo.dto.MemoResponseDto
import com.example.checkcheck.memo.service.MemoService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "메모 Api 컨트롤러", description =  "메모 생성,조회,수정,삭제 Api 명세서입니다.")
@RestController
@RequestMapping("/api/memos")

class MemoController (
    private val memoService: MemoService
){

    /**
     * 모든 메모를 조회하는 Api
     */
    @GetMapping
    private fun getMemos() : ResponseEntity<BaseResponse<List<MemoResponseDto>>> {
        val result = memoService.getMemos()
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
    * 메모를 생성하는 Api
    */
    @PostMapping
    private fun postMemo(@Valid @RequestBody memoRequestDto: MemoRequestDto) :
            ResponseEntity<BaseResponse<MemoResponseDto>> {
        val result = memoService.postMemos(memoRequestDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse(data = result))
    }

    /**
     * 메모 수정 Api
     */
    @PutMapping("/{id}")
    private fun putMemo(@Valid @RequestBody memoRequestDto: MemoRequestDto, @PathVariable id : Long) :
            ResponseEntity<BaseResponse<MemoResponseDto>> {
        val result = memoService.putMemos(memoRequestDto, id)
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse(data = result))
    }

    /**
     * 메모 삭제 Api
     */
    @DeleteMapping("/{id}")
    private fun deleteMemo(@PathVariable id : Long) : ResponseEntity<BaseResponse<Any>> {
        memoService.deleteMemo(id)
        return ResponseEntity.ok().body(BaseResponse(data = null))
    }

}