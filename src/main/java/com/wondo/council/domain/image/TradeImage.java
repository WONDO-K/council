package com.wondo.council.domain.image;

import com.wondo.council.domain.Trade;
import com.wondo.council.domain.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="trade_images")
public class TradeImage {
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

    @Column(name = "delete_yn")
    private Boolean delete_yn;

    @ManyToOne(targetEntity = Trade.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_uid")
    private Trade trade;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uid")
    private User user;

}
