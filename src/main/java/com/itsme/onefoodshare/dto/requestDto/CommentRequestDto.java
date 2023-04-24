package com.itsme.onefoodshare.dto.requestDto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentRequestDto {
    @NotNull
    private Long postId;
    private String content;
    private String createTime;
}
