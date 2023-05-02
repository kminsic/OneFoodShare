package com.itsme.onefoodshare.service;

import com.itsme.onefoodshare.Repository.JoinRequestRepository;
import com.itsme.onefoodshare.entity.JoinRequest;
import com.itsme.onefoodshare.entity.Post;
import com.itsme.onefoodshare.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
@RequiredArgsConstructor
public class JoinService {
    private final JoinRequestRepository joinRequestRepository;

    public void createJoinRequest(Post post, User user) {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setPost(post);
        joinRequest.setUser(user);
        joinRequest.setAccepted(false);
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
