package com.itsme.onefoodshare.controller;


import com.itsme.onefoodshare.entity.JoinRequest;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import com.itsme.onefoodshare.service.JoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth/join")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createJoinRequest(@RequestParam Long postId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

        joinService.createJoinRequest(postId, userDetailsImpl);
        return ResponseEntity.ok("참가요청을 했습니다!");
    }

    @GetMapping("/{postId}/requests")
    public List<JoinRequest> getJoinRequests(@PathVariable Long postId) {
        return joinService.getJoinRequests(postId);
    }

    @PostMapping("/{requestId}/accept")
    public ResponseEntity<String> acceptJoinRequest(@PathVariable Long requestId) {
        joinService.acceptJoinRequest(requestId);
        return ResponseEntity.ok("참가요청이 수락되었습니다!");
    }

    @PostMapping("/{requestId}/reject")
    public ResponseEntity<String> rejectJoinRequest(@PathVariable Long requestId) {
        joinService.rejectJoinRequest(requestId);
        return ResponseEntity.ok("참가요청이 거부되었습니다");
    }
}
