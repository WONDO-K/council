package com.wondo.council.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="inquiries")
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private Long uid;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "up_date")
    private String upDate;

    @Column(name = "views")
    private int view;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uid")
    private User user;

//    @OneToMany(mappedBy = "inquiry", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<InquiryReply> replies;

    public void addViewCount() {
        this.view +=1;
    }
}
