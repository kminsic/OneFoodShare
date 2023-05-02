package com.itsme.onefoodshare.entity;

import com.itsme.onefoodshare.dto.requestDto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(nullable = false)
    private int authorNum;

    @Column
    private int joinAuthor;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String location;

    @Column
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private int postAmount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();



    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.authorNum = postRequestDto.getAuthorNum();
        this.joinAuthor = postRequestDto.getJoinAuthor();
        this.location = postRequestDto.getLocation();
        this.postAmount = postRequestDto.getPostAmount();
        this.image = postRequestDto.getImage();
    }


    // Getters and setters
}
