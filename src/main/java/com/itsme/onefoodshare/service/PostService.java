package com.itsme.onefoodshare.service;



import com.itsme.onefoodshare.Repository.JoinRequestRepository;
import com.itsme.onefoodshare.Repository.PostRepository;
import com.itsme.onefoodshare.dto.requestDto.PostRequestDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.dto.responseDto.PostResponseDto;
import com.itsme.onefoodshare.entity.JoinRequest;
import com.itsme.onefoodshare.entity.Post;
import com.itsme.onefoodshare.entity.User;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JoinRequestRepository joinRequestRepository;

    //포스트 생성
    @Transactional
    public GlobalResDto createPost(PostRequestDto postDto, UserDetailsImpl userDetails){
        if (null == userDetails.getAuthorities()) {
            return  new GlobalResDto("올바른 사용자가 아닙니다.",
                    HttpStatus.BAD_REQUEST.value());
        }

        Post post = Post.builder()
                .author(userDetails.getUser())
                .authorNum(postDto.getAuthorNum())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .location(postDto.getLocation())
                .image(postDto.getImage())
                .postAmount(postDto.getPostAmount())
                .createdAt(LocalDateTime.now())
                .build();
           postRepository.save(post);

           return new GlobalResDto("게시물 작성에 성공했습니다.", HttpStatus.OK.value());
    }

    //포스트 수정
    @Transactional
    public GlobalResDto updatePost(Long id, PostRequestDto postDto, UserDetailsImpl userDetails){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다"));
        if(!userDetails.getId().equals(post.getAuthor().getId())) {
            return new GlobalResDto("올바른 사용자가 아닙니다", HttpStatus.BAD_REQUEST.value());
        }
        if (null == userDetails.getAuthorities()) {
            return  new GlobalResDto("올바른 사용자가 아닙니다.",
                    HttpStatus.BAD_REQUEST.value());
        }
        post.update(postDto);
        return new GlobalResDto("게시물 수정에 성공했습니다.", HttpStatus.OK.value());
    }

    //포스트 삭제
    @Transactional
    public GlobalResDto deletePost(Long id,UserDetailsImpl userDetails){
        if(null==userDetails.getAuthorities()){
            return new GlobalResDto("사용자를 찾을수 없습니다",HttpStatus.OK.value());
        }
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다"));
        postRepository.deleteById(post.getId());
        return new GlobalResDto("삭제가 완료되었습니다", HttpStatus.OK.value());
    }

    //포스트 상세 보기
    @Transactional
    public ResponseEntity<?> getDetailPost(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다"));

        return ResponseEntity.ok(
                PostResponseDto.builder()
                        .id(post.getId())
                        .image(post.getImage())
                        .author(post.getAuthor().getUsername())
                        .authorNum(post.getAuthorNum())
                        .title(post.getTitle())
                        .postAmount(post.getPostAmount())
                        .build());

    }

    public void createJoinRequest(Post post, User user) {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setPost(post);
        joinRequest.setUser(user);
        joinRequest.setAccepted(false);
        joinRequestRepository.save(joinRequest);
    }

    public List<JoinRequest> getJoinRequests(Long postId) {
        return joinRequestRepository.findByPostId(postId);
    }

    public void acceptJoinRequest(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("JoinRequest not found"));
        joinRequest.setAccepted(true);
        joinRequestRepository.save(joinRequest);
    }

    public void rejectJoinRequest(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("JoinRequest not found"));
        joinRequestRepository.delete(joinRequest);
    }


}
