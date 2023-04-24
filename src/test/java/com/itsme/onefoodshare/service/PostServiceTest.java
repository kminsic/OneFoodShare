package com.itsme.onefoodshare.service;

import com.itsme.onefoodshare.Repository.PostRepository;
import com.itsme.onefoodshare.Repository.UserRepository;
import com.itsme.onefoodshare.dto.requestDto.PostRequestDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.entity.Post;
import com.itsme.onefoodshare.entity.User;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

//    @Test
//    @DisplayName("Should return an error message when the user has no authorities")
//    void createPostWhenUserHasNoAuthorities() {
//        PostRequestDto postDto =
//                new PostRequestDto("author", "title", "content", "image", "location", "createdAt");
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        userDetails.setAccount(new User());
//        when(userDetails.getAuthorities()).thenReturn(null);
//
//        GlobalResDto result = postService.createPost(postDto, userDetails);
//
//        assertEquals("사용자를 찾을 수 없습니다.", result.getMsg());
//        assertEquals(HttpStatus.OK.value(), result.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("Should create a post successfully when the user has authorities")
//    void createPostWhenUserHasAuthorities() {
//        PostRequestDto postDto =
//                new PostRequestDto("author", "title", "content", "image", "location", "createdAt");
//        User user = new User();
//        user.setUsername("username");
//        user.setEmail("email@example.com");
//
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        userDetails.setAccount(user);
//
//        GlobalResDto response = postService.createPost(postDto, userDetails);
//
//        assertEquals("게시물 작성에 성공했습니다.", response.getMsg());
//        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
//
//        // Verify that the save method was called once
//        verify(postRepository, times(1)).save(any(Post.class));
//    }
}