package com.wondo.council.domain.image;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vote_images")
public class VoteImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="uid")
    private Long uid;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "saved_name")
    String savedName;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "file_size")
    Long fileSize;

    @Column(name = "belong")
    private String belong;

    @Column(name = "belong_uid")
    private Long belong_uid;

}
