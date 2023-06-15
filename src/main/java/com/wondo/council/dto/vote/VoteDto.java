package com.wondo.council.dto.vote;

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

    private String title;

    private String content;

    private String imgUrl;


    public static VoteDto from(Vote vote){
        return VoteDto.builder()
                .title(vote.getTitle())
                .content(vote.getContent())
                .imgUrl(vote.getImageUrl())
                .build();
    }


}
