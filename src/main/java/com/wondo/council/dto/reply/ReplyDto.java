package com.wondo.council.dto.reply;

import com.wondo.council.domain.Reply;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "ReplyResponseDto",description = "댓글 정보 Dto")
public class ReplyDto {
    private Long uid;
    private String content;
    private String regDate;
    private String nickname;

    public static ReplyDto from(Reply reply){
        return ReplyDto.builder()
                .uid(reply.getUid())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .nickname(reply.getUser().getNickname())
                .build();
    }
}
