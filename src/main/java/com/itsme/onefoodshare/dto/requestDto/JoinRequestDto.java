package com.itsme.onefoodshare.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class JoinRequestDto {
    private Long postId;
    private Long userId;
}
