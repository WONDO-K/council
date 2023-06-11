package com.wondo.council.service;

import com.wondo.council.domain.Reply;
import com.wondo.council.dto.reply.ReplyDto;
import com.wondo.council.dto.reply.ReplyRequestDto;

import java.util.List;

public interface ReplyService {
   Reply createReply(Long uid, ReplyRequestDto replyRequestDto);

   List<ReplyDto> getReplyList(Long uid);

   void deleteReply(Reply reply);
}
