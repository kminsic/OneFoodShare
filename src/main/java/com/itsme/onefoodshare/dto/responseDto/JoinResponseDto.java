package com.itsme.onefoodshare.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class JoinRequestResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private Boolean accepted;
}
