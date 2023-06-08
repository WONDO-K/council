package com.wondo.council.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private Long uid;

    @Column(name = "content")
    private String content;

    // 해당 댓글의 자식 댓글의 수
    @Column(name = "childNum")
    private Long childNum;

    @Column(name = "del_yn")
    private Boolean del_yn;

    @Column(name = "reg_date")
    private String regDate;

    // 부모 댓글의 UID 값
    @Column(name = "parentNum")
    private Long parentNum;

    // 댓글 그룹
    @Column(name = "ref")
    private Long ref;

    // 댓글 그룹들의 순서
    @Column(name = "refOrder")
    private Long refOrder;

    // 댓글의 계층
    @Column(name = "step")
    private Long step;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uid")
    private User user;

    @ManyToOne(targetEntity = Article.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "article_uid")
    private Article article;


}
