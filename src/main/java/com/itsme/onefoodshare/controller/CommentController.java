package com.itsme.onefoodshare.controller;

import com.itsme.onefoodshare.dto.requestDto.CommentRequestDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import com.itsme.onefoodshare.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class CommentController {

    private final CommentService commentService;

    //댓글 조회
    @GetMapping("/comments/{id}")
    public ResponseEntity<?> searchPostComment(@PathVariable Long id){
        return commentService.searchPostComment(id);
    }
    // 댓글 생성
    @PostMapping("/comment")
    public GlobalResDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(commentRequestDto, userDetails);
    }
    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public GlobalResDto deleteComment(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails);
    }
}
