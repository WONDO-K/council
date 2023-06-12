package com.wondo.council.service;

import com.wondo.council.domain.Reply;
import com.wondo.council.dto.reply.ReplyDto;
import com.wondo.council.dto.reply.ReplyRequestDto;
import com.wondo.council.dto.reply.ReplyUpdateRequestDto;

import java.util.List;

public interface ReplyService {
   Reply createReply(Long uid, ReplyRequestDto replyRequestDto);

   void deleteReply(Reply reply);

   List<ReplyDto> getReplyList(Long uid);

   void updateReply(Long uid, ReplyUpdateRequestDto replyUpdateRequestDto);
}
