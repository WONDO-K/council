package com.wondo.council.dto.vote;

import com.wondo.council.domain.User;
import com.wondo.council.domain.Vote;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Builder
@ApiModel(value = "VoteDto",description = "투표 안건 정보 Dto")
@Log4j2
public class VoteDto {

    private Long uid;

    private String title;

    private String content;

    private String date;

    private String imgUrl;

    private String nickname;


    public static VoteDto from(Vote vote){
        String theDate;
        if(vote.getUpDate() == null){
            theDate = vote.getRegDate();
        } else {
            theDate = vote.getUpDate();
        }
        return VoteDto.builder()
                .uid(vote.getUid())
                .title(vote.getTitle())
                .content(vote.getContent())
                .date(theDate)
                .imgUrl(vote.getImageUrl())
                .nickname(vote.getUser().getNickname())
                .build();
    }


}
