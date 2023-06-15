package com.wondo.council.dto.vote;

import com.wondo.council.domain.Vote;
import com.wondo.council.domain.VoteRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@ApiModel(value = "VoteDto",description = "투표 안건 정보 Dto")
public class VoteDto {

    private String title;

    private String content;

    private String imgUrl;

    private int yesCount;

    private int noCount;


    public static VoteDto from(Vote vote){
        return VoteDto.builder()
                .title(vote.getTitle())
                .content(vote.getContent())
                .imgUrl(vote.getImageUrl())
                .build();
    }

    public static VoteDto closedFrom(Vote vote){
        return VoteDto.builder()
                .title(vote.getTitle())
                .content(vote.getContent())
                .imgUrl(vote.getImageUrl())
                .yesCount(vote.getYesCount())
                .noCount(vote.getNoCount())
                .build();
    }

}
