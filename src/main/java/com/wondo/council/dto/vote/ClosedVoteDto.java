package com.wondo.council.dto.vote;

import com.wondo.council.domain.Vote;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "ClosedVoteDto", description = "종료된 투표 안건 정보 Dto")
public class ClosedVoteDto {

    private String title;

    private String content;

    private String imgUrl;

    @ApiModelProperty(value = "찬성 횟수")
    private int yesCount;

    @ApiModelProperty(value = "반대 횟수")
    private int noCount;

    public static ClosedVoteDto from(Vote vote){
        return ClosedVoteDto.builder()
                .title(vote.getTitle())
                .content(vote.getContent())
                .imgUrl(vote.getImageUrl())
                .yesCount(vote.getYesCount())
                .noCount(vote.getNoCount())
                .build();
    }
}
