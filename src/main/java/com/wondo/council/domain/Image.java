package com.wondo.council.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="images")
public class Image {

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

}
