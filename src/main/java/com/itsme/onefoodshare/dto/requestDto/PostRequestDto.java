package com.itsme.onefoodshare.dto.requestDto;

import lombok.*;

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
    private String createdAt;
    private int postAmount;


}
