package com.itsme.onefoodshare.dto.responseDto;


import com.itsme.onefoodshare.entity.User;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponseDto {

    private Long id;
    private User author;
    private String content;
    private LocalDateTime createdAt;
}