package com.itsme.onefoodshare.controller;

import com.itsme.onefoodshare.dto.requestDto.CommentRequestDto;
import com.itsme.onefoodshare.dto.responseDto.CommentResponseDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import com.itsme.onefoodshare.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentResponseDto>> searchPostComment(@PathVariable Long id) {
        List<CommentResponseDto> comments = commentService.searchPostComment(id);
        return ResponseEntity.ok(comments);
    }

    // 댓글 생성
    @PostMapping("/comment")
    public ResponseEntity<GlobalResDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        GlobalResDto result = commentService.createComment(commentRequestDto, userDetails);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<GlobalResDto> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        GlobalResDto result = commentService.deleteComment(id, userDetails);
        return ResponseEntity.status(result.getStatus()).body(result);
    }
}
