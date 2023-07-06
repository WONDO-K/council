package com.wondo.council.dto.trade;

import com.wondo.council.domain.Trade;
import com.wondo.council.domain.enums.TradeCategory;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Getter
@Builder
@ApiModel(value = "TradeDto",description = "거래 게시글 정보 Dto")
@Log4j2
public class TradeDto {

    private Long uid;

    private String title;

    private String content;

    private int price;

    private String date;

    private List<String> imageUrls;

    private int view;

    private TradeCategory tradeCategory;

    private String nickname;

    public static TradeDto from(Trade trade){
        String theDate;
        if(trade.getUpDate() == null){
            theDate = trade.getRegDate();
        } else {
            theDate = trade.getUpDate();
        }
        return TradeDto.builder()
                .uid(trade.getUid())
                .title(trade.getTitle())
                .content(trade.getContent())
                .price(trade.getPrice())
                .date(theDate)
                .imageUrls(trade.getImageUrls())
                .view(trade.getView())
                .tradeCategory(trade.getTradeCategory())
                .nickname(trade.getUser().getNickname())
                .build();
    }

}
