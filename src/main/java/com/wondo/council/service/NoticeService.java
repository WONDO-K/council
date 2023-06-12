package com.wondo.council.service;

import com.wondo.council.dto.notice.NoticeRequestDto;

public interface NoticeService {
    void createNotice(NoticeRequestDto noticeRequestDto);

    void updateNotice(Long uid, NoticeRequestDto noticeRequestDto);
}
