package com.itsme.onefoodshare.Repository;

import com.itsme.onefoodshare.entity.Comment;
import com.itsme.onefoodshare.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);
}
