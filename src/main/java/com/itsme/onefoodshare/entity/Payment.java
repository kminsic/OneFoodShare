package com.itsme.onefoodshare.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User payer;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Getters and setters
}
