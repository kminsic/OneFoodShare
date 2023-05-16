package com.itsme.onefoodshare.dto.requestDto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {
    private String author;
    private int authorNum;
    private String title;
    private String content;
    private String image;
    private String location;
    private LocalDateTime createdAt;
    private int joinAuthor;
    private int postAmount;


}
