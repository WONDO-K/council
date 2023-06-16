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
    private Long uid;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "closed")
    private boolean closed;

    @Column(name = "yes_count")
    private int yesCount;

    @Column(name = "no_count")
    private int noCount;

    @OneToMany(mappedBy = "vote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VoteRecord> voteRecords;

    public void addYesCount(){
        this.yesCount+=1;
    }

    public void addNoCount(){
        this.noCount+=1;
    }

    public void setClosed(boolean isClosed){
        this.closed = isClosed;
    }
}
