package com.itsme.onefoodshare.dto.responseDto;


import com.itsme.onefoodshare.entity.User;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Builder
@Getter
public class CommentResponseDto {
    private Long id;
    private User author;

    private String content;

    private LocalDateTime createdAt;
}
