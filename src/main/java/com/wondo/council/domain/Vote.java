package com.wondo.council.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "closed")
    private boolean closed;

    @OneToMany(mappedBy = "vote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VoteRecord> voteRecords;
}
