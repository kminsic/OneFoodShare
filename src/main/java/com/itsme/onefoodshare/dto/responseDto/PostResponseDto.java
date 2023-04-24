package com.itsme.onefoodshare.dto.responseDto;

import lombok.Builder;

import java.util.List;

@Builder
public class PostResponseDto {
    private Long id;
    private List<CommentResponseDto> commentResponseDtoList;
    private String author;
    private int authorNum;
    private String title;
    private String content;
    private String image;
    private String location;
    private String createdAt;
    private int postAmount;
}
