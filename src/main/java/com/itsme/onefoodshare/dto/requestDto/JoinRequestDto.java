package com.itsme.onefoodshare.dto.requestDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequestDto {
    private Long postId;
    private Long userId;
}
