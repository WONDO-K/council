package com.wondo.council.domain.enums;

public enum Category {
    디지털기기("디지털기기"),
    생활가전("생활가전"),
    가구인테리어("가구/인테리어"),
    생활주방("생활/주방"),
    유아동("유.아동"),
    도서("도서"),
    여성의류("여성의류"),
    여성잡화("여성잡화"),
    남성패션잡화("남성패션/잡화"),
    뷰티미용("뷰티/미용"),
    스포츠레저("스포츠/레저"),
    취미게임음반("취미/게임/음반"),
    티켓교환권("티켓/교환권"),
    가공식품("가공식품"),
    반려동물("반려동물"),
    식물("식물"),
    기타중고물품("기타 중고물품"),
    삽니다("삽니다");

    private String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
