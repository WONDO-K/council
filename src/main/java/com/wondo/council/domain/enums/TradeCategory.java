package com.wondo.council.domain.enums;

import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;

@Log4j2
public enum TradeCategory {
    DIGITAL_DEVICES("디지털기기"),
    HOUSEHOLD_APPLIANCES("생활가전"),
    FURNITURE_INTERIORS("가구/인테리어"),
    LIVING_KITCHEN("생활/주방"),
    CHILD("유.아동"),
    BOOK("도서"),
    WOMAN_CLOTHES("여성의류"),
    WOMAN_MISCELLANEOUS("여성잡화"),
    MAN_CLOTHES_MISCELLANEOUS("남성패션/잡화"),
    BEAUTY("뷰티/미용"),
    SPORTS_LEISURE("스포츠/레저"),
    HOBBY_GAME_RECORDINGS("취미/게임/음반"),
    TICKET("티켓/교환권"),
    PROCESSED_FOOD("가공식품"),
    ANIMAL("반려동물"),
    PLANT("식물"),
    ETC("기타 중고물품"),
    BUY("삽니다");

    private String label;

    TradeCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static TradeCategory transCategory(String tradeCategory) {
        List<TradeCategory> categoryList = Arrays.asList(TradeCategory.values());

        for(TradeCategory categories:categoryList){
            if (tradeCategory.equals(categories.toString())){
                return categories;
            }
        }
        log.info("카테고리 오류");
        throw new IllegalArgumentException();
    }
}
