package com.itsme.onefoodshare.service;


import com.itsme.onefoodshare.Repository.CommentRepository;
import com.itsme.onefoodshare.Repository.PostRepository;
import com.itsme.onefoodshare.dto.requestDto.CommentRequestDto;
import com.itsme.onefoodshare.dto.responseDto.CommentResponseDto;
import com.itsme.onefoodshare.dto.responseDto.GlobalResDto;
import com.itsme.onefoodshare.entity.Comment;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
        private final CommentRepository commentRepository;
        private final PostRepository postRepository;
        private final UserDetailsServiceImpl userDetailsServiceImpl;

    //포스트 해당 코멘트 조회
    @Transactional
    public ResponseEntity<?> searchpostComment(Long id) {
        Post post = findByPostId(id);
        return ResponseEntity.ok(findComment(post));
    }
    //댓글 작성
    @Transactional
    public GlobalResDto createComment(CommentRequestDto commentRequestDto, UserDetailsImpl userDetails){
        User user = userDetailsServiceImpl.findByUser(userDetails.getUser().getId());
        Optional<Post> post = postRepository.findById(commentRequestDto.getPostId());
        if(post.isEmpty()){
            return new GlobalResDto("해당 포스트가 존재하지않습니다.", HttpStatus.BAD_REQUEST.value());
        }
        Comment comment = Comment.builder()
                .author(user)
                .createdAt(LocalDateTime.parse(commentRequestDto.getCreateTime()))
                .content(commentRequestDto.getContent())
                .build();
        commentRepository.save(comment);
        return new GlobalResDto("작성에 성공했습니다.", HttpStatus.OK.value());

    }
    @Transactional
    public GlobalResDto deleteComment(Long id , UserDetailsImpl userDetails){
        Optional<Comment> comment = commentRepository.findById(userDetails.getId());
        if (!comment.get().getAuthor().equals(userDetails.getUser())){
            return new GlobalResDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
        }
        commentRepository.delete(comment);
        return new GlobalResDto("삭제 성공", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true)
    public Post findByPostId(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 포스트입니다."));
    }
    public List<CommentResponseDto> findComment(Post post) {
        List<Comment> commentList = commentRepository.findAllByPostOrderByCreatedAtDesc(post);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build()
            );
        }
        return commentResponseDtoList;
    }
}
