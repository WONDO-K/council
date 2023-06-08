package com.wondo.council.service;

import com.wondo.council.domain.Reply;
import com.wondo.council.dto.reply.ReplyRequestDto;

public interface ReplyService {
   Reply createReply(Long uid, ReplyRequestDto replyRequestDto);
}
