package com.itsme.onefoodshare.controller;

import com.itsme.onefoodshare.dto.requestDto.PostRequestDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.dto.responseDto.PostResponseDto;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import com.itsme.onefoodshare.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<GlobalResDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        GlobalResDto response = postService.createPost(postRequestDto, userDetailsImpl);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/post/{id}")
    public ResponseEntity<GlobalResDto> updatePost(@RequestBody PostRequestDto postRequestDto,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                   @PathVariable Long id) {
        GlobalResDto response = postService.updatePost(id, postRequestDto, userDetailsImpl);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<GlobalResDto> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                                   @PathVariable Long id) {
        GlobalResDto response = postService.deletePost(id, userDetailsImpl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto response = postService.getDetailPost(id).getBody();
        return ResponseEntity.ok(response);
    }
}
