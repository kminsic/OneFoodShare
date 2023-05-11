package com.itsme.onefoodshare.controller;

import com.itsme.onefoodshare.dto.requestDto.PostRequestDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
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
    public GlobalResDto createPost(@RequestBody PostRequestDto postRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return postService.createPost(postRequestDto, userDetailsImpl);
    }

    @PatchMapping("/post/{id}")
    public GlobalResDto updatePost(@RequestBody PostRequestDto postRequestDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                   @PathVariable Long id) {
        return postService.updatePost(id, postRequestDto,userDetailsImpl);
    }
    @DeleteMapping("/post/{id}")
    public GlobalResDto deletePost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                   @PathVariable Long id) {
        return postService.deletePost(id,userDetailsImpl);
    }
    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return postService.getDetailPost(id);
    }

}
