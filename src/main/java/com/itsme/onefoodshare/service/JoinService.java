package com.itsme.onefoodshare.service;

import com.itsme.onefoodshare.Repository.JoinRequestRepository;
import com.itsme.onefoodshare.entity.JoinRequest;
import com.itsme.onefoodshare.entity.Post;
import com.itsme.onefoodshare.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final JoinRequestRepository joinRequestRepository;


    public void createJoinRequest(Long postId, UserDetailsImpl userDetails) {
        JoinRequest joinRequest = JoinRequest.builder()
                .user(userDetails.getUser())
                .post(Post.builder().id(postId).build())
                .build();
        joinRequestRepository.save(joinRequest);

    }

    public List<JoinRequest> getJoinRequests(Long postId) {
        return joinRequestRepository.findByPostId(postId);
    }

    public void acceptJoinRequest(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("JoinRequest not found"));
        joinRequest.setAccepted(true);
        joinRequestRepository.save(joinRequest);
    }

    public void rejectJoinRequest(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("JoinRequest not found"));
        joinRequestRepository.delete(joinRequest);
    }
}
